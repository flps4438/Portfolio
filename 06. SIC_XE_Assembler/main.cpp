# include <iostream>
# include <vector>
# include <stack>
# include <fstream>
#include <iomanip>

using namespace std;


struct Token{
    string s;
    int tableNum{};
    int position{};
};

struct Line{
    int lineNumber{};
    string readString;
    vector<Token> tokenList;
    string errorString = "";
    bool error = false;
};

struct Identifier{
    string identifer = "";
    int subroutine = -1;
    int type = -1;
    int pointer = -1;
};

struct Location{

    int tableNum = -1;
    int position = -1;
};

struct Intermediate{
    Location oper;
    Location op1;
    Location op2;
    Location result;
    string message;
};

struct LabelForward{
    string s;
    vector<int> index;
};


class BasicTable {

public:
    explicit BasicTable(string fileName);
    int getPosition( string wantFind );
    bool isInTable( string wantFind );

private:
    fstream file;

    string fileName;
    vector<string> tableList;

    void createTable();
};

BasicTable::BasicTable( string fileName ) {

    this->fileName = fileName;
    createTable();
}

void BasicTable::createTable() {

    file.open( fileName.c_str(), ios::in );

    if ( file ) {

        string readString;

        tableList.emplace_back("");

        while( ! file.eof() ) {

            file >> readString;

            if ( readString == "%" )

                tableList.push_back(" ");

            else tableList.push_back( readString );
        }

        // cout << fileName << " create success!" << endl;
    }
    else
        cout << fileName << " not find!" << endl;

}

int BasicTable::getPosition( string wantFind ) {

    for ( int i = 1 ; i < tableList.size() ; i ++ ) {

        if ( tableList.at(i) == wantFind )

            return i;
    }

    return -1;
}

bool BasicTable::isInTable( string wantFind ) {

    return getPosition( wantFind ) != -1;
}

// ------------------------------------------------------------------------------------------------------

class SymbolTable{

public:
    explicit SymbolTable( int size );

    bool addSymbol( string addName, bool check );

    int getPosition( string wantFind, int scope );
    bool isInTable( string wantFind, int scope );
    Identifier* getIdentifier(string wantGet, int scope);
    Identifier* getIdentifier(int position, int scope);

private:
    fstream file;

    Identifier *tableList;
    int size;

    int getHashPostion( string wantFind );


};

SymbolTable::SymbolTable( int size ) {

    this->size = size;

    tableList = new Identifier[size];

    for ( int i = 0 ; i < size ; i ++ )

        tableList[i].identifer = "";

}

int SymbolTable::getHashPostion( string wantFind ) {

    int sum = 0;

    for ( char c : wantFind )

        sum += c;

    return sum % 100;
}

bool SymbolTable::addSymbol(string addName, bool check ) {

    int addPosition = getHashPostion( addName );
    int start = addPosition - 1;

    if ( start == -1 ) start = size - 1;

    for ( int i = addPosition ; true ; i ++ ) {

        if ( i >= size )

            i -= size;

        if ( tableList[i].identifer.empty()) {

            tableList[i].identifer = addName;

            return true;
        }
        else if ( tableList[i].identifer == addName && check ) {

            return false;
        }

        if ( i == start ) break;
    }

    cout << "Hash Table is Full. Fail to add s = \"" << addName << "\"" << endl;

    return false;
}

int SymbolTable::getPosition(string wantFind, int scope) {

    int addPosition = getHashPostion( wantFind );
    int start = addPosition - 1;

    if ( start == -1 ) start = size - 1;

    for ( int i = addPosition ; true ; i ++ ) {

        if ( i >= size )

            i -= size;

        if ( tableList[i].identifer == wantFind && tableList[i].subroutine == scope )

            return i;

        else if ( tableList[i].identifer.empty() )

            return -1;

        if ( start == i ) return -1;
    }
}

bool SymbolTable::isInTable(string wantFind, int scope) {

    return getPosition(wantFind, scope) != -1;
}

Identifier* SymbolTable::getIdentifier(string wantGet, int scope) {

    int position = getPosition(wantGet, scope);

    if ( position != -1 ) return &tableList[position];
    else {

        Identifier *identifier;

        identifier->identifer = "-1";

        return identifier;
    }
}

Identifier *SymbolTable::getIdentifier(int position, int scope) {

    if ( tableList[position].subroutine == scope ) return &tableList[position];
    else {

        Identifier *identifier;

        identifier->identifer = "-1";

        return identifier;
    }
}

// ------------------------------------------------------------------------------------------------

class SyntaxAnalysis{

public:
    explicit SyntaxAnalysis( vector<Line *> lineList, SymbolTable *integerTable);
    void start();
    void printAns();


private:

    Line *nowLine;

    vector<Line *> lineList;
    SymbolTable *symbolTable;
    SymbolTable *integerTable;
    vector<Intermediate> intermediateTable;
    vector<int> informationTable;
    vector<Token> table0;
    vector<int> callForward;
    vector<LabelForward> labelForward;

    bool haveError = false;

    int nowScope = -1;
    int mainScope = -1;
    string statementErrorString;

    string programErrorString;
    string variableErrorString;
    string dimensionErrorString;
    string labelErrorString;
    string gtoErrorString;
    string subroutineErrorString;
    string callErrorString;
    string assignmentErrorString;
    string ifErrorString;
    string arrayErrorString;
    string inputErrorString;
    string outputErrorString;
    string enpErrorString;
    string ensErrorString;

    void checkSyntax(vector<Token> tokenList);
    bool isProgram(vector<Token> tokenList);
    bool isVariable(vector<Token> tokenList);
    bool isDimension(vector<Token> tokenList);
    bool isLabel(vector<Token> tokenList);
    bool isGTO(vector<Token> tokenList);
    bool isSubroutine(vector<Token> tokenList);
    bool isCall(vector<Token> tokenList);
    bool isAssignment(vector<Token> tokenList, bool ifUse, bool arrayUse);
    bool isIF(vector<Token> tokenList);
    bool arrayProcess(vector<Token> tokenList);
    bool isInput(vector<Token> tokenList);
    bool isOutput(vector<Token> tokenList);
    bool isENP(vector<Token> tokenList);
    bool isENS(vector<Token> tokenList);

    int getSize(Token t, bool haveLBracket, bool ifUse);
};

SyntaxAnalysis::SyntaxAnalysis(vector<Line *> lineList, SymbolTable *integerTable) {

    this->lineList = lineList;
    this->integerTable = integerTable;

}

void SyntaxAnalysis::start() {

    table0.clear();

    Token init0;

    table0.push_back(init0);

    callForward.clear();
    labelForward.clear();

    symbolTable = new SymbolTable(100);

    informationTable.clear();

    informationTable.push_back(-1);

    intermediateTable.clear();

    Intermediate init;
    init.oper.tableNum = -1;
    init.oper.position = -1;

    intermediateTable.push_back(init);

    vector<Token> newTokenList;
    bool haveEnd = false;


    for ( Line * line : lineList ) {

        nowLine = line;
        haveEnd = false;
        vector<Token> tokenList = line->tokenList;

        for ( Token t : tokenList ) {

            newTokenList.push_back(t);

            if ( t.s != ";" );
            else {

                checkSyntax(newTokenList);

                // if ( line->error ) break;

                newTokenList.clear();

                haveEnd = true;
            }
        }

        if ( ! haveEnd ) {

            line->error = true;

            if ( line->errorString == "" )

                line->errorString = "Syntax Error : 最後字元非結束指令 ';'";
        }

        // if ( line->error ) break;
    }

}

void SyntaxAnalysis::checkSyntax(vector<Token> tokenList) {

    haveError = false;

    if ( isProgram(tokenList) ) {

        if ( programErrorString != "" ) {

            haveError = true;
            statementErrorString = programErrorString;
        }
    }
    else if ( isVariable(tokenList) ) {

        if ( variableErrorString != "" ) {

            haveError = true;
            statementErrorString = variableErrorString;
        }
    }
    else if ( isDimension(tokenList) ) {

        if ( dimensionErrorString != "" ) {

            haveError = true;
            statementErrorString = dimensionErrorString;
        }

    }
    else if ( isLabel(tokenList) ) {

        if ( labelErrorString != "" ) {

            haveError = true;
            statementErrorString = labelErrorString;
        }
    }
    else if ( isGTO(tokenList) ) {

        if ( gtoErrorString != "" ) {

            haveError = true;
            statementErrorString = gtoErrorString;
        }
    }
    else if ( isSubroutine(tokenList) ) {

        if ( subroutineErrorString != "" ) {

            haveError = true;
            statementErrorString = subroutineErrorString;
        }

    }
    else if ( isCall(tokenList) ) {

        if ( callErrorString != "" ) {

            haveError = true;
            statementErrorString = callErrorString;
        }
    }
    else if ( isInput(tokenList) ) {

        if ( inputErrorString != "" ) {

            haveError = true;
            statementErrorString = inputErrorString;
        }

    }
    else if ( isOutput(tokenList) ) {

        if ( outputErrorString != "" ) {

            haveError = true;
            statementErrorString = outputErrorString;
        }

    }
    else if ( isIF(tokenList) ) {

        if ( ifErrorString != "" ) {

            haveError = true;
            statementErrorString = ifErrorString;
        }

    }
    else if ( isENP(tokenList) ) {

        if ( enpErrorString != "" ) {

            haveError = true;
            statementErrorString = enpErrorString;
        }
    }
    else if ( isENS(tokenList) ) {

        if ( ensErrorString != "" ) {

            haveError = true;
            statementErrorString = ensErrorString;
        }
    }
    else {

        isAssignment(tokenList, false, false);

        if ( assignmentErrorString != "" ) {

            statementErrorString = assignmentErrorString;
            haveError = true;
        }
    }

    if ( haveError ) {

        if ( nowLine->errorString == "" )

            nowLine->errorString = statementErrorString;

        nowLine->error = true;
    }
}

bool SyntaxAnalysis::isProgram(vector<Token> tokenList) {

    int round = 1;

    programErrorString = "";

    bool program = false;

    for ( Token t : tokenList ) {

        if ( round == 1 ) {

            if (t.s == "PROGRAM") program = true;
            else return false;
        }
        else if ( round == 2 ) {

            if ( program ) {

                if ( t.tableNum == 5 ) {

                    symbolTable->addSymbol(t.s, true);
                    Identifier *identifier = symbolTable->getIdentifier(t.s, -1);
                    identifier->pointer = intermediateTable.size();

                    nowScope = symbolTable->getPosition(t.s, -1);
                    mainScope = nowScope;
                }
                else {
                    programErrorString = "Syntax Error : PROGRAM 後沒有 Symbol";
                    return true;
                }
            }
            else return false;
        }
        else if ( round == 3 ) {

            if ( t.s != ";" ) {

                programErrorString = "Syntax Error : Program 後出現多餘字串";
                return true;
            }
            else return true;
        }
        else programErrorString = "Syntax Error : Program 後出現多餘字串";

        round ++;
    }

    return program;
}

bool SyntaxAnalysis::isVariable(vector<Token> tokenList) {

    int round = 1;

    variableErrorString = "";

    bool variable = false;
    int type = -1;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "VARIABLE") variable = true;
            else return false;

        } else if (round == 2) {

            if (variable) {

                if ( t.s == "ARRAY" ) type = 1;
                else if ( t.s == "BOOLEAN" ) type = 2;
                else if ( t.s == "CHARACTER" ) type = 3;
                else if ( t.s == "INTEGER" ) type = 4;
                else if ( t.s == "LABEL" ) type = 5;
                else if ( t.s == "REAL" ) type = 6;
                else {

                    variableErrorString = "Syntax error : type error | " + t.s + "非合法 Type 名稱";
                    return true;
                }
            }
            else ; // not do anything
        }
        else if ( round == 3 ) {

            if ( t.s == ":" ) ;
            else {

                variableErrorString = "Syntax Error : 少一個 ':' ";
                return true;
            }
        }
        else if ( round == 4 ) {

            if ( t.tableNum == 5 ) {

                symbolTable->addSymbol(t.s, true);
                Identifier *identifier = symbolTable->getIdentifier(t.s, -1);
                identifier->subroutine = nowScope;
                identifier->type = type;

                Intermediate intermediate;
                intermediate.oper.tableNum = 5;
                intermediate.oper.position = symbolTable->getPosition(t.s, nowScope);
                intermediate.message = t.s;

                intermediateTable.push_back(intermediate);
            }
            else {

                variableErrorString = "Syntax error : " + t.s + " 非變數名稱";
                return true;
            }
        }
        else if ( round == 5 ) {

            if ( t.s == ";") ;
            else if ( t.s == "," ) {

                round -= 2;
            }
            else {

                variableErrorString = "Syntax Error : 出現多餘字串 " + t.s;
                return true;
            }
        }

        round++;
    }

    return variable;
}

bool SyntaxAnalysis::isDimension(vector<Token> tokenList) {

    int round = 1;

    string arrayName;

    dimensionErrorString = "";

    bool dimension = false;
    int type = -1;
    vector<int> tableInformation;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "DIMENSION") dimension = true;
            else return false;

        } else if (round == 2) {

            if (dimension) {

                type = true;

                if ( t.s == "ARRAY" ) type = 1; // TODO: ERROR?
                else if ( t.s == "BOOLEAN" ) type = 2;
                else if ( t.s == "CHARACTER" ) type = 3;
                else if ( t.s == "INTEGER" ) type = 4;
                else if ( t.s == "LABEL" ) type = 5;
                else if ( t.s == "REAL" ) type = 6;
                else {

                    dimensionErrorString = "Syntax error : type error | " + t.s + "非合法 Type 名稱";
                    return true;
                }
            }
            else ; // not do anything
        }
        else if ( round == 3 ) {

            if ( t.s == ":");
            else {

                dimensionErrorString = "Syntax error : After type 缺少 ':'";
                return true;
            }
        }
        else if ( round == 4 ) {

            if ( t.tableNum == 5 ) {

                arrayName = t.s;
                tableInformation.clear();
            }
            else {

                dimensionErrorString = "Syntax error : 非合法的變數名稱";
                return true;
            }
        }
        else if ( round == 5 ) {

            if ( t.s == "(" ) ;
            else {

                dimensionErrorString = "Syntax error : 缺少了 '('";
                return true;
            }
        }
        else if ( round == 6 ) {

            if ( t.tableNum == 3 )

                tableInformation.push_back(stoi(t.s));

            else {

                dimensionErrorString = "Syntax error : 缺少了 number (array size)";
                return true;
            }
        }
        else if ( round == 7 ) {

            if ( t.s == "," )

                round -= 2;

            else if ( t.s == ")" ) {

                int start = informationTable.size();
                informationTable.push_back(type);
                informationTable.push_back(tableInformation.size());

                for ( int i : tableInformation )

                    informationTable.push_back(i);

                tableInformation.clear();

                symbolTable->addSymbol(arrayName, true);
                Identifier *identifier = symbolTable->getIdentifier(arrayName, -1);
                identifier->subroutine = nowScope;
                identifier->type = 1;
                identifier->pointer = start;

                Intermediate intermediate;
                intermediate.oper.tableNum = 5;
                intermediate.oper.position = symbolTable->getPosition(arrayName, nowScope);
                intermediate.message = arrayName;

                intermediateTable.push_back(intermediate);
            }
            else {

                dimensionErrorString = "Syntax error : 缺少了 ')' or ',' ";
                return true;
            }
        }
        else if ( round == 8 ) {

            if ( t.s == ";" ) return true;
            else if ( t.s == "," )

                round -= 5;

            else {

                dimensionErrorString = "Syntax error : 缺少了 ',' or ';' ";
                return true;
            }
        }

        round ++;
    }

    return dimension;
}

bool SyntaxAnalysis::isLabel(vector<Token> tokenList) {

    int round = 1;

    labelErrorString = "";

    bool label = false;


    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "LABEL") label = true;
            else return false;

        } else if (round == 2) {

            if ( t.tableNum == 5 ) {

                symbolTable->addSymbol(t.s, true);
                Identifier *identifier = symbolTable->getIdentifier(t.s, -1);
                identifier->subroutine = nowScope;
                identifier->type = 5;

                Intermediate intermediate;
                intermediate.oper.tableNum = 5;
                intermediate.oper.position = symbolTable->getPosition(t.s, nowScope);
                intermediate.message = t.s;

                intermediateTable.push_back(intermediate);
            }
            else {

                labelErrorString = "Syntax error : " + t.s + " 非變數名稱";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == "," )

                round -= 2;

            else if ( t.s == ";" ) return true;
            else {

                labelErrorString = "Syntax error : 缺少 ',' or ';' ";
                return true;
            }

        }

        round ++;
    }

    return label;
}

bool SyntaxAnalysis::isGTO(vector<Token> tokenList) {

    int round = 1;

    gtoErrorString = "";

    bool gto = false;


    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "GTO") gto = true;
            else return false;

        } else if (round == 2) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                if ( identifier->type == 5 ) {

                    Intermediate intermediate;
                    intermediate.oper.tableNum = 2;
                    intermediate.oper.position = 11;
                    intermediate.result.tableNum = 6;
                    intermediate.result.position = identifier->pointer;

                    intermediate.message = "GTO " + t.s;
                    intermediateTable.push_back(intermediate);

                    if ( identifier->pointer == -1 ) {

                        bool find = false;
                        LabelForward tl;

                        for ( LabelForward l : labelForward ) {

                            if ( l.s == t.s ) {
                                find = true;
                                tl = l;
                            }
                        }

                        if ( ! find ) {

                            tl.s = t.s;
                            tl.index.push_back(intermediateTable.size() - 1);
                            labelForward.push_back(tl);
                        }

                        tl.index.push_back(intermediateTable.size() - 1);
                    }
                }
                else {

                    gtoErrorString = "Syntax error : " + t.s + " 非宣告過的 Label";
                    return true;
                }
            }
            else {

                gtoErrorString = "Syntax error : " + t.s + " 非 Label 名稱 ";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == ";" ) return true;
            else {

                gtoErrorString = "Syntax error : 最後字元非結束指令 ';'";
                return true;
            }
        }

        round ++ ;
    }
    return gto;
}

bool SyntaxAnalysis::isSubroutine(vector<Token> tokenList) {

    int round = 1;

    subroutineErrorString = "";

    bool subroutine = false;
    int type = -1;


    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "SUBROUTINE") subroutine = true;
            else return false;

        } else if (round == 2) {

            if ( t.tableNum == 5 ) {

                symbolTable->addSymbol(t.s, true);
                Identifier *identifier = symbolTable->getIdentifier(t.s, -1);
                identifier->pointer = intermediateTable.size();

                for ( int p : callForward ) {

                    Intermediate *i = &intermediateTable[p];

                    if ( i->oper.tableNum == 2 && i->oper.position == 3 && i->op1.tableNum == 5 && i->op1.position == -1 )

                        i->op1.position = symbolTable->getPosition(t.s, -1);
                }

                nowScope = symbolTable->getPosition(t.s, -1);
            }
            else {

                subroutineErrorString = "Syntax error : 變數名稱錯誤 " + t.s ;
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == "(" ) ;
            else {

                subroutineErrorString = "Syntax error : 缺少 '('";
                return true;
            }
        }
        else if ( round == 4 ){

            if ( t.s == "ARRAY" ) type = 1;
            else if ( t.s == "BOOLEAN" ) type = 2;
            else if ( t.s == "CHARACTER" ) type = 3;
            else if ( t.s == "INTEGER" ) type = 4;
            else if ( t.s == "LABEL" ) type = 5;
            else if ( t.s == "REAL" ) type = 6;
            else {

                subroutineErrorString = "Syntax error : type error | " + t.s + "非合法 Type 名稱";
                return true;
            }
        }
        else if ( round == 5 ) {

            if ( t.s == ":" ) ;
            else {

                subroutineErrorString = "Syntax error : 缺少 ':'";
                return true;
            }
        }
        else if ( round == 6 ) {

            if( t.tableNum == 5 ) {

                symbolTable->addSymbol(t.s, false);
                Identifier *identifier = symbolTable->getIdentifier(t.s, -1);
                identifier->subroutine = nowScope;
                identifier->type = type;

                Intermediate intermediate;
                intermediate.oper.tableNum = 5;
                intermediate.oper.position = symbolTable->getPosition(t.s, nowScope);
                intermediate.message = t.s;

                intermediateTable.push_back(intermediate);
            }
            else {

                subroutineErrorString = "Syntax error : " + t.s + "非變數名稱";
                return true;
            }
        }
        else if ( round == 7 ) {

            if ( t.s == "," ) round -= 2;
            else if ( t.s == ")" ) ;
            else {

                subroutineErrorString = "Syntax error : 缺少 ',' or ')' ";
                return true;
            }
        }
        else if ( round == 8 ) {

            if ( t.s == ";" ) ;
            else {

                subroutineErrorString = "Syntax error : 缺少 ';'";
                return true;
            }
        }

        round ++;
    }

    return subroutine;
}

bool SyntaxAnalysis::isCall(vector<Token> tokenList) {

    int round = 1;

    callErrorString = "";

    bool call = false;

    string name;
    vector<int> information;
    string message = "CALL ";


    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "CALL") call = true;
            else return false;

        } else if (round == 2) {

            if ( t.tableNum == 5 ) {

                name = t.s;

                message += name;
            }
            else {

                callErrorString = "Syntax error : 文法錯誤";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == "(" ) {

                information.clear();
                message = message + "(";
            }
            else {

                callErrorString = "Syntax error : 缺少 '(' ";
                return true;
            }

        }
        else if ( round == 4 ) {

            if ( (t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) )  || t.tableNum == 3 || t.tableNum == 4 ) {

                information.push_back(t.tableNum);
                information.push_back(t.position);

                message += t.s;

            }
            else {

                callErrorString = "Syntax error : " + t.s + " 未定義的變數名稱";
                return true;
            }
        }
        else if ( round == 5 ) {

            if ( t.s == "," ) {
                round -= 2;
                message += ", ";
            }
            else if ( t.s == ")" ) {

                 message += ")";

                int start = informationTable.size();
                informationTable.push_back(information.size() / 2 );

                for ( int i : information )

                    informationTable.push_back(i);

                Intermediate intermediate;
                intermediate.oper.tableNum = 2;
                intermediate.oper.position = 3;

                intermediate.op1.tableNum = 5;
                intermediate.op1.position = symbolTable->getPosition(name, -1);

                if ( intermediate.op1.position == -1 ) callForward.push_back(intermediateTable.size());

                intermediate.result.tableNum = 7;
                intermediate.result.position = start;

                intermediate.message = message;

                intermediateTable.push_back(intermediate);
            }
            else {

                callErrorString = "Syntax error : 缺少 ',' or ')' ";
                return true;

            }
        }
        else if ( round == 6 ) {

            if ( t.s == ";" ) return true;
            else {

                callErrorString = "Syntax error : 最後字元非結束指令 ';' ";
                return true;
            }
        }
        round ++;
    }

    return call;
}

bool SyntaxAnalysis::isAssignment(vector<Token> tokenList, bool ifUse, bool arrayUse) {

    int round = 1;

    assignmentErrorString = "";

    bool assignment = false;
    bool symbol = false;
    bool readArray = false;
    bool oneArray = false;
    bool equal = false;

    stack<Token> operandStack;
    stack<Token> operatorStack;

    vector<Token> arraryTokenList;

    int leftBracket = 0;
    int arrayLeftBracket = 0;


    for (Token t : tokenList) {

        if (round == 1 && ! arrayUse) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                if ( identifier->type == 5 ) {

                    identifier->pointer = intermediateTable.size();

                    for ( LabelForward l : labelForward ) {

                        if ( l.s == t.s ) {

                            for ( int i : l.index )

                                intermediateTable[i].result.position = intermediateTable.size();

                            break;
                        }
                    }

                    round --;
                }

                if ( ! readArray ) symbol = true;
            }
            else {

                assignmentErrorString = "Syntax error : 開頭非 id";
                return false;
            }
        }

        if( t.s == ";" );

        else if ( round != 0 || arrayUse ) {

            if ( t.tableNum == 5  && ! readArray ) {

                t.position = symbolTable->getPosition(t.s, nowScope);

                operandStack.push(t);

                if ( ! readArray ) symbol = true;

            }
            else if ( t.tableNum == 3 || t.tableNum == 4 && ! readArray )

                operandStack.push(t);

            else if ( symbol && t.s == "(" && ! readArray ) {

                readArray = true;
                arrayLeftBracket = 1;

                arraryTokenList.push_back(operandStack.top());
                operandStack.pop();

                arraryTokenList.push_back(t);
            }
            else if ( readArray ) {

                if ( t.s == "(" ) {

                    arrayLeftBracket ++;
                }
                else if ( t.s == ")" )

                    arrayLeftBracket --;

                arraryTokenList.push_back(t);

                if ( arrayLeftBracket == 0 ) {

                    if ( arraryTokenList.size() > 4 || equal ) {

                        arrayProcess(arraryTokenList);

                        if (arrayErrorString != "") {

                            assignmentErrorString = arrayErrorString;

                            return true;
                        }

                        Intermediate intermediate = intermediateTable.back();

                        Token temp;

                        temp.s = "T" + to_string(intermediate.result.position);
                        temp.tableNum = intermediate.result.tableNum;
                        temp.position = intermediate.result.position;

                        operandStack.push(temp);
                    }
                    else {

                        operandStack.push(arraryTokenList[0]);
                        operandStack.push(arraryTokenList[2]);

                        oneArray = true;
                    }

                    readArray = false;
                    symbol = false;
                    arraryTokenList.clear();
                    arrayLeftBracket = 1;
                }

            }
            else if ( ( t.tableNum == 1 && ! ifUse ) || ( t.tableNum == 2 && ifUse ) ) {

                if ( symbol ) if ( t.s != "(" ) symbol = false;
                if ( t.s == "=" ) equal = true;

                if ( operatorStack.empty() ) {

                    operatorStack.push(t);

                    if ( t.s == "(" )

                        leftBracket ++;
                }
                else if ( t.s == "(" ) {

                    operatorStack.push(t);
                    leftBracket ++;
                }
                else {

                    Token top = operatorStack.top();

                    int topSize = getSize(top, leftBracket > 0, ifUse);
                    int tSize = getSize(t, leftBracket > 0, ifUse);

                    if ( topSize == 0 || tSize == 0 ) return true;

                    if ( t.s == ")" && leftBracket > 0 ) {

                        while ( operatorStack.top().s != "(" ) {

                            if ( ! operandStack.empty() && ! operatorStack.empty() ) {

                                Token op2 = operandStack.top();
                                operandStack.pop();

                                if ( operandStack.empty() ) {

                                    assignmentErrorString = "Syntax error : 少一個 operand";
                                    return true;
                                } // error

                                Token op1 = operandStack.top();
                                operandStack.pop();

                                Token oper = operatorStack.top();
                                operatorStack.pop();

                                Intermediate intermediate;

                                intermediate.op1.tableNum = op1.tableNum;
                                intermediate.op1.position = op1.position;

                                intermediate.oper.tableNum = oper.tableNum;
                                intermediate.oper.position = oper.position;

                                intermediate.op2.tableNum = op2.tableNum;
                                intermediate.op2.position = op2.position;

                                int size = table0.size();
                                string name = "T" +  to_string(size);

                                Token temp;
                                temp.s = name;
                                temp.tableNum = 0;
                                temp.position = size;

                                table0.push_back(temp);
                                operandStack.push(temp);

                                intermediate.result.tableNum = 0;
                                intermediate.result.position = size;

                                intermediate.message = name + " = " + op1.s + " " + oper.s + " " + op2.s;

                                intermediateTable.push_back(intermediate);
                            }
                            else {

                                assignmentErrorString = "Syntax error : 文法錯誤";
                                return true;
                            }
                        }

                        operatorStack.pop();
                        leftBracket-- ;
                    }
                    else if ( t.s == ")" ) {

                        assignmentErrorString = "Syntax error : ')' 前沒有出現 '(' ";
                        return true;
                    }
                    else if ( tSize < topSize ){

                        while ( tSize <= topSize ) {

                            if (!operandStack.empty() && !operatorStack.empty()) {

                                Token op2 = operandStack.top();
                                operandStack.pop();

                                if (operandStack.empty()) {

                                    assignmentErrorString = "Syntax error : 少一個 operand";
                                    return true;
                                } // error

                                Token op1 = operandStack.top();
                                operandStack.pop();

                                Token oper = operatorStack.top();
                                operatorStack.pop();

                                Intermediate intermediate;

                                intermediate.op1.tableNum = op1.tableNum;
                                intermediate.op1.position = op1.position;

                                intermediate.oper.tableNum = oper.tableNum;
                                intermediate.oper.position = oper.position;

                                intermediate.op2.tableNum = op2.tableNum;
                                intermediate.op2.position = op2.position;

                                int size = table0.size();
                                string name = "T" +  to_string(size);

                                Token temp;
                                temp.s = name;
                                temp.tableNum = 0;
                                temp.position = size;

                                table0.push_back(temp);
                                operandStack.push(temp);

                                intermediate.result.tableNum = 0;
                                intermediate.result.position = size;

                                intermediate.message = name + " = " + op1.s + " " + oper.s + " " + op2.s;

                                intermediateTable.push_back(intermediate);

                                if ( ! operatorStack.empty() ) {

                                    top = operatorStack.top();
                                    topSize = getSize(top, leftBracket > 0, ifUse);
                                }
                                else

                                    break;

                            } else {

                                assignmentErrorString = "Syntax error : 文法錯誤";
                                return true;
                            }
                        }

                        operatorStack.push(t);

                    }
                    else {

                        operatorStack.push(t);
                    }
                }
            }
            else {

                assignmentErrorString = "Syntax error : 不合法的語法";
                return true;
            }
        }

        round ++ ;
    }


    vector<Token> operandList;

    int limit = 1;

    if ( ifUse ) limit = 0;
    else if ( oneArray ) limit = 2;
    else limit = 1;

    while ( operandStack.size() > limit ) {

        operandList.push_back(operandStack.top());
        operandStack.pop();
    }

    for (Token t : operandList)

        operandStack.push(t);

    vector<Token> operatorList;

    while ( operatorStack.size() > limit ) {

        operatorList.push_back(operatorStack.top());
        operatorStack.pop();
    }

    for (Token t : operatorList)

        operatorStack.push(t);


    while ( ! operandStack.empty() && ! operatorStack.empty() ) {

        Token op2 = operandStack.top();
        operandStack.pop();

        if ( operandStack.empty() ) {

            assignmentErrorString = "Syntax error : 少一個 operand";
            return true;

        } // error

        Token op1 = operandStack.top();
        operandStack.pop();

        Token oper = operatorStack.top();
        operatorStack.pop();

        Token result;

        if ( oneArray && operandStack.size() == 1 ) {

            result = operandStack.top();
            operandStack.pop();
        }

        Intermediate intermediate;

        intermediate.op1.tableNum = op2.tableNum;
        intermediate.op1.position = op2.position;

        intermediate.oper.tableNum = oper.tableNum;
        intermediate.oper.position = oper.position;

        if ( operandStack.empty() && oper.s == "=" ) {

            string message = "";

            if ( ! oneArray ) {

                intermediate.result.tableNum = op1.tableNum;
                intermediate.result.position = op1.position;

                 message = op1.s + " = " + op2.s;
            }
            else {

                intermediate.result.tableNum = op1.tableNum;
                intermediate.result.position = op1.position;

                intermediate.op2.tableNum = result.tableNum;
                intermediate.op2.position = result.position;

                message = result.s + "(" + op1.s + ") = " + op2.s;
            }

            intermediate.message = message;

            intermediateTable.push_back(intermediate);
        }
        else if ( operandStack.empty() && ! ifUse && ! arrayUse ) {

            assignmentErrorString = "Syntax error : 找不到 =";
            return true;
        }
        else {

            intermediate.op2.tableNum = op1.tableNum;
            intermediate.op2.position = op1.position;

            int size = table0.size();
            string name = "T" + to_string(size);

            Token temp;
            temp.s = name;
            temp.tableNum = 0;
            temp.position = size;

            table0.push_back(temp);
            operandStack.push(temp);

            intermediate.result.tableNum = 0;
            intermediate.result.position = size;

            intermediate.message = name + " = " + op2.s + " " + oper.s + " " + op1.s;

            intermediateTable.push_back(intermediate);
        }
    }

    return true;
}

int SyntaxAnalysis::getSize(Token t, bool haveLBracket, bool ifUse) {

    if ( haveLBracket ) {

        if ( ! ifUse ) {
            if (t.s == "=") return 1;
            else if (t.s == "(" || t.s == ")") return 2;
            else if (t.s == "+" || t.s == "-") return 3;
            else if (t.s == "*" || t.s == "/") return 4;
            else if (t.s == "^") return 5;
            else {
                assignmentErrorString = "Syntax error : 錯誤的 operator";
                return 0;
            }
        }
        else {

            if ( t.s == "(" || t.s == ")" ) return 2;
            else if ( t.s == "AND" || t.s == "EQ" || t.s == "GE" || t.s == "GT"
                      || t.s == "LE" || t.s == "LT" || t.s == "NE" || t.s == "OR" ) return 3;
            else {
                ifErrorString = "Syntax error : 錯誤的 operator";
                return 0;
            }
        }
    }
    else {

        if ( ! ifUse ) {

            if (t.s == "=") return 1;
            else if (t.s == ")") return 2;
            else if (t.s == "+" || t.s == "-") return 3;
            else if (t.s == "*" || t.s == "/") return 4;
            else if (t.s == "^") return 5;
            else if (t.s == "(") return 6;
            else {
                assignmentErrorString = "Syntax error : 錯誤的 operator";
                return 0;
            }
        }
        else {
            if ( t.s == ")" ) return 2;
            else if ( t.s == "AND" || t.s == "EQ" || t.s == "GE" || t.s == "GT"
                      || t.s == "LE" || t.s == "LT" || t.s == "NE" || t.s == "OR" ) return 3;
            else if ( t.s == "(" ) return 6;
            else {
                ifErrorString = "Syntax error : 錯誤的 operator";
                return 0;
            }
        }

    }

    return -1;
}

bool SyntaxAnalysis::isIF(vector<Token> tokenList) {

    int round = 1;

    ifErrorString = "";
    statementErrorString = "";
    assignmentErrorString = "";

    int nowITSize = 0;

    bool isif = false;
    bool haveGto = false;

    vector<Token> conditionList;
    vector<Token> statementList;

    Intermediate intermediate;
    Intermediate *intermediateAfter;

    Intermediate gto;
    Intermediate *gtoAfter;

    int leftBracket = 0;

    for ( Token t : tokenList ) {

        if ( round == 1 ) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if ( t.s == "IF" ) {

                isif = true;
            }
            else return false;
        }
        else if ( round == 2 ) {

            if ( t.s == "THEN" ) {

                if ( isAssignment(conditionList, true, false) ) {

                    if ( assignmentErrorString == "" ) round ++;

                    else {

                        ifErrorString = assignmentErrorString;
                        return true;
                    }

                } else {

                    ifErrorString = assignmentErrorString;
                    return true;
                }
            }
            else conditionList.push_back(t);

            round --;
        }
        else if ( round == 3 ) {

            statementList.push_back(t);

            intermediate.oper.tableNum = 2;
            intermediate.oper.position = 12;

            nowITSize = intermediateTable.size();

            Location l = intermediateTable[intermediateTable.size() - 1].result;

            intermediate.op1.tableNum = l.tableNum;
            intermediate.op1.position = l.position;

            intermediate.op2.tableNum = 6;
            intermediate.op2.position = intermediateTable.size() + 1;

            intermediate.message = "IF";

            intermediateTable.push_back(intermediate);

            intermediateAfter = &intermediateTable[intermediateTable.size() - 1];
        }
        else if ( round == 4 ) {

            if ( t.s == "ELSE" ) {

                Token t1;
                t1.s = ";";
                statementList.push_back(t1);

                checkSyntax(statementList);

                if ( haveError ) {

                    ifErrorString = statementErrorString;

                    return true;
                }
                else {

                    if ( intermediateTable.size() == nowITSize ) {

                        // 應該不可能
                        intermediateAfter->op2.tableNum = -1;
                        intermediateAfter->op2.position = -1;
                    }
                    else {

                        intermediateAfter->result.tableNum = 6;
                        intermediateAfter->result.position = intermediateTable.size() + 1;
                    }

                    if ( statementList[0].s != "GTO" ) {

                        Intermediate gto;

                        gto.oper.tableNum = 2;
                        gto.oper.position = 11;

                        intermediateTable.push_back(gto);
                        gtoAfter = &intermediateTable[intermediateTable.size() - 1];

                        haveGto = true;
                    }

                    statementList.clear();

                    round ++;
                }

            }
            else statementList.push_back(t);

            round--;
        }
        else if ( round == 5 ) {

            nowITSize = intermediateTable.size();

            if ( t.s == ";" ) {

                statementList.push_back(t);

                checkSyntax(statementList);

                if ( haveError ) {

                    ifErrorString = statementErrorString;

                    return true;
                }
                else if ( haveGto ) {

                    gtoAfter->result.tableNum = 6;
                    gtoAfter->result.position = intermediateTable.size();
                }

                round++;
            }
            else statementList.push_back(t);

            round--;
        }
        else {

            ifErrorString = "Syntax error :  最後字元非結束指令 ';' ";
            return true;
        }

        round ++;
    }

    return isif;
}

bool SyntaxAnalysis::arrayProcess(vector<Token> tokenList) {

    int round = 1;

    arrayErrorString = "";
    assignmentErrorString = "";

    bool array = false;
    bool indexRead = false;

    string name;

    int size;
    int indexLeftBracket = 0;

    vector<int> sizeArray;

    vector<Token> indexList;
    vector<Token> indexTokenList;


    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope)
                    && symbolTable->getIdentifier(t.s, nowScope)->type == 1 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                int start = identifier->pointer;

                size = informationTable[start + 1];

                for ( int i = 0 ; i < size ; i ++ )

                    sizeArray.push_back(informationTable[i + size + 2]);
            }
            else {

                arrayErrorString = "Syntax error : " + t.s + " 此陣列未被宣告";
                return true;
            }
        }
        else if ( round == 2 ) {

            if ( t.s == "(" ) ;
            else {

                arrayErrorString = "Syntax error : 缺少 '(' ";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == "(" && indexLeftBracket == 0) {

                indexLeftBracket ++;
                indexTokenList.clear();

                indexRead = true;

                round --;
            }
            else if (  t.s == "(" ) {

                indexLeftBracket ++;
                indexTokenList.push_back(t);

                round --;
            }
            else if ( t.s == ")" ) {

                indexLeftBracket --;

                if ( indexLeftBracket != 0 ) {

                    indexTokenList.push_back(t);

                    round --;
                }
                else {

                    checkSyntax(indexTokenList);

                    if ( haveError ) {

                        arrayErrorString = statementErrorString;
                        return true;
                    }
                    else {

                        Intermediate intermediate = intermediateTable.back();

                        Token temp;

                        temp.tableNum = intermediate.result.tableNum;
                        temp.position = intermediate.result.position;

                        if ( temp.tableNum == 0 )

                            temp.s = "T" + to_string(t.position);

                        else temp.s = symbolTable->getIdentifier(temp.position, nowScope)->identifer;

                        indexList.push_back(temp);
                    }
                }
            }
            else if ( indexRead ) {

                indexTokenList.push_back(t);

                round --;
            }
            else if ( t.tableNum == 5 || t.tableNum == 3 || t.tableNum == 4 )

                indexList.push_back(t);
        }
        else if ( round == 4 ) {

            if ( t.s == "," ) {

                round -= 2;

            }
            else if ( t.s == ")" ) {

                if ( indexList.size() != size ) {

                    arrayErrorString = "Syntax error : 陣列大小不一致 ";
                    return true;
                }
                else {

                    vector<Token> calculateList;
                    Token t_l;
                    Token t_r;
                    Token t_1;
                    Token t_add;
                    Token t_sub;
                    Token t_m;
                    Token t_end;

                    t_l.s = "(";
                    t_l.tableNum = 1;
                    t_l.position = 2;

                    t_r.s = ")";
                    t_r.tableNum = 1;
                    t_r.position = 3;

                    t_1.s = "1";
                    t_1.tableNum = 3;

                    if ( ! integerTable->isInTable("1", -1) )

                        integerTable->addSymbol("1", true);

                    t_1.position = integerTable->getPosition("1", -1 );

                    t_add.s = "+";
                    t_add.tableNum = 1;
                    t_add.position = 5;

                    t_sub.s = "-";
                    t_sub.tableNum = 1;
                    t_sub.position = 6;

                    t_m.s = "*";
                    t_m.tableNum = 1;
                    t_m.position = 7;

                    t_end.s = ";";
                    t_end.tableNum = 1;
                    t_end.position = 1;


                    for ( int i = indexList.size() - 1 ; i >= 0 ; i -- ) {

                        if ( i != 0 ) {

                            calculateList.push_back(t_l);
                            calculateList.push_back(indexList[i]);
                            calculateList.push_back(t_sub);
                            calculateList.push_back(t_1);
                            calculateList.push_back(t_r);
                        }
                        else calculateList.push_back(indexList[i]);

                        for ( int j = i - 1 ; j >= 0 && i != 0; j -- ) {

                            calculateList.push_back(t_m);

                            Token temp;
                            temp.s = to_string(sizeArray[j]);
                            temp.tableNum = 3;

                            if ( ! integerTable->isInTable(temp.s, -1) )

                                integerTable->addSymbol(temp.s, true);

                            temp.position = integerTable->getPosition(temp.s, -1 );

                            calculateList.push_back(temp);
                        }

                        if ( i != 0 ) calculateList.push_back(t_add);

                    }

                    calculateList.push_back(t_end);

                    Token op2;

                    if ( calculateList.size() > 2 ) {

                        isAssignment(calculateList, false, true);

                        if (assignmentErrorString != "") {

                            arrayErrorString = assignmentErrorString;

                            return true;
                        }

                        Intermediate intermediate = intermediateTable.back();

                        op2.s = "T" + to_string(intermediate.result.position);
                        op2.tableNum = intermediate.result.tableNum;
                        op2.position = intermediate.result.position;

                    }
                    else op2 = calculateList[0];

                    Token op1 = tokenList[0];

                    Token oper;

                    oper.s = "=";
                    oper.tableNum = 1;
                    oper.position = 4;

                    Intermediate intermediate;

                    intermediate.op1.tableNum = op1.tableNum;
                    intermediate.op1.position = op1.position;

                    intermediate.oper.tableNum = oper.tableNum;
                    intermediate.oper.position = oper.position;

                    intermediate.op2.tableNum = op2.tableNum;
                    intermediate.op2.position = op2.position;

                    int size = table0.size();
                    string name = "T" + to_string(size);

                    Token temp;
                    temp.s = name;
                    temp.tableNum = 0;
                    temp.position = size;

                    table0.push_back(temp);

                    intermediate.result.tableNum = 0;
                    intermediate.result.position = size;

                    intermediate.message = name + " = " + op1.s + "(" + op2.s + ")";

                    intermediateTable.push_back(intermediate);

                }
            }
            else {

                arrayErrorString = "Syntax error : 缺少 ')' ";
                return true;
            }
        }
        else {

            arrayErrorString = "Syntax error : 文法錯誤";

            return true;
        }

        round++;
    }

    return true;
}

bool SyntaxAnalysis::isInput(vector<Token> tokenList) {

    int round = 1;

    inputErrorString = "";

    bool input = false;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "INPUT") input = true;
            else return false;

        } else if ( round == 2 ) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) ) {

                Intermediate intermediate;

                intermediate.oper.tableNum = 2;
                intermediate.oper.position = 13;

                intermediate.result.tableNum = t.tableNum;
                intermediate.result.position = t.position;

                intermediate.message = "INPUT " + t.s;

                intermediateTable.push_back(intermediate);
            }
            else {

                inputErrorString = "Syntax error : 變數 " + t.s + " 未被定義";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == ";" ) return true;
            else {

                inputErrorString = "Syntax error : 最後字元非結束指令 ';' ";
                return true;
            }
        }
        else {

            inputErrorString = "Syntax error : 最後字元非結束指令 ';' ";
            return true;
        }

        round ++;
    }

    return input;
}

bool SyntaxAnalysis::isOutput(vector<Token> tokenList) {

    int round = 1;

    outputErrorString = "";

    bool output = false;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "OUTPUT") output = true;
            else return false;

        } else if ( round == 2 ) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) ) {

                Intermediate intermediate;

                intermediate.oper.tableNum = 2;
                intermediate.oper.position = 20;

                intermediate.result.tableNum = t.tableNum;
                intermediate.result.position = t.position;

                intermediate.message = "OUTPUT " + t.s;

                intermediateTable.push_back(intermediate);
            }
            else {

                outputErrorString = "Syntax error : 變數 " + t.s + " 未被定義";
                return true;
            }
        }
        else if ( round == 3 ) {

            if ( t.s == ";" ) return true;
            else {

                outputErrorString = "Syntax error : 最後字元非結束指令 ';' ";
                return true;
            }
        }
        else {

            outputErrorString = "Syntax error : 最後字元非結束指令 ';' ";
            return true;
        }

        round ++;
    }

    return output;
}

bool SyntaxAnalysis::isENP(vector<Token> tokenList) {

    int round = 1;

    enpErrorString = "";

    bool enp = false;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "ENP" && nowScope == mainScope) {

                enp = true;

                Intermediate intermediate;

                intermediate.oper.tableNum = 2;
                intermediate.oper.position = 6;

                intermediate.message = "ENP";

                intermediateTable.push_back(intermediate);
            }
            else if (t.s == "ENP") {

                enpErrorString = "Syntax error : 目前不在 main function 內";
                return true;
            }
            else return false;

        } else if (round == 2) {

            if ( t.s == ";" ) return true;
            else {

                enpErrorString = "Syntax error : 最後字元非結束指令 ';' ";
                return true;
            }
        }
        else{

            enpErrorString = "Syntax error : 最後字元非結束指令 ';' ";
            return true;
        }

        round ++;
    }

    return enp;
}

bool SyntaxAnalysis::isENS(vector<Token> tokenList) {

    int round = 1;

    ensErrorString = "";

    bool ens = false;

    for (Token t : tokenList) {

        if (round == 1) {

            if ( t.tableNum == 5 && symbolTable->isInTable(t.s, nowScope) && symbolTable->getIdentifier(t.s, nowScope)->type == 5 ) {

                Identifier *identifier = symbolTable->getIdentifier(t.s, nowScope);

                identifier->pointer = intermediateTable.size();

                for ( LabelForward l : labelForward ) {

                    if ( l.s == t.s ) {

                        for ( int i : l.index )

                            intermediateTable[i].result.position = intermediateTable.size();

                        break;
                    }
                }

                round --;
            }
            else if (t.s == "ENS" && nowScope != mainScope) {

                ens = true;

                Intermediate intermediate;

                intermediate.oper.tableNum = 2;
                intermediate.oper.position = 7;

                intermediate.message = "ENS";

                intermediateTable.push_back(intermediate);
            }
            else if (t.s == "ENS") {

                ensErrorString = "Syntax error : 目前不在 main function 內";
                return true;
            }
            else return false;

        } else if (round == 2) {

            if ( t.s == ";" ) return true;
            else {

                ensErrorString = "Syntax error : 最後字元非結束指令 ';' ";
                return true;
            }
        }
        else{

            ensErrorString = "Syntax error : 最後字元非結束指令 ';' ";
            return true;
        }

        round ++;
    }

    return ens;
}

void SyntaxAnalysis::printAns() {

    fstream file;
    string fileName = "";

    cout << endl << "Intermediate Code Generation Success! enter output file name :";
    cin >> fileName;

    while ( fileName == "" ) {

        cout << endl << "Intermediate Code Generation Success! enter output file name :";
        cin >> fileName;
    }

    file.open( fileName, ios::out );

    int count = 0;

    cout << endl;

    for ( Intermediate i : intermediateTable ) {

        if ( count == 0 ) ;
        else {

            cout << count << "\t(";
            file << count << "\t(";

            cout << setw(6) << "(" + to_string(i.oper.tableNum) + "," + to_string(i.oper.position) << ")";
            file << setw(6) << "(" + to_string(i.oper.tableNum) + "," + to_string(i.oper.position) << ")";

            if ( i.op1.tableNum != -1 ) {

                cout << "," << setw(7) << "(" + to_string(i.op1.tableNum) + "," + to_string(i.op1.position) + ")";
                file << "," << setw(7) << "(" + to_string(i.op1.tableNum) + "," + to_string(i.op1.position) + ")";
            }
            else {

                cout << "," << setw(7) << " ";
                file << "," << setw(7) << " ";
            }

            if ( i.op2.tableNum != -1 ) {

                cout << ","  << setw(7) << "(" + to_string(i.op2.tableNum) + "," + to_string(i.op2.position) + ")";
                file << ","  << setw(7) << "(" + to_string(i.op2.tableNum) + "," + to_string(i.op2.position) + ")";
            }
            else {

                cout << "," << setw(7) << " ";
                file << "," << setw(7) << " ";
            }

            if ( i.result.tableNum != -1 ) {

                cout << "," << setw(7) << "(" + to_string(i.result.tableNum) + "," + to_string(i.result.position) + ")";
                file << "," << setw(7) << "(" + to_string(i.result.tableNum) + "," + to_string(i.result.position) + ")";
            }
            else {

                cout << "," << setw(7) << " ";
                file << "," << setw(7) << " ";
            }

            cout << " )" << "\t" << i.message << endl;
            file << " )" << "\t" << i.message << endl;
        }

        count ++;
    }

    cout << endl;
    file << endl;

    for ( Line * line : lineList ) {

        if (line->error) {

            cout << "Line " << line->lineNumber << "\t" << line->errorString << endl;
            file << "Line " << line->lineNumber << "\t" << line->errorString << endl;
        }
    }

    cout << endl;
}

class LexicalAnalysis {

public:
    explicit LexicalAnalysis( int hashTableSize );
    bool readInput( string dataName );
    void printAns( string fileName );
    SyntaxAnalysis* createSyntaxAnalysis();

private:

    fstream file;

    bool isSpace( char c );
    bool isLineSpace( char c );
    bool isNumber( char c );
    bool isAllNumber( string s );
    bool isRealNumber( string s );

    BasicTable *delimiterTable, *instructionTable;
    SymbolTable *symbolTable;
    SymbolTable *integerTable;
    SymbolTable *realNumberTable;
    vector<Line *> lineList;

    Token createToken( string s );

};

LexicalAnalysis::LexicalAnalysis( int hashTableSize ) {

    delimiterTable = new BasicTable("table1.table");
    instructionTable = new BasicTable("table2.table");
    symbolTable = new SymbolTable(100);

    integerTable = new SymbolTable(100);
    realNumberTable = new SymbolTable(100);
}

bool LexicalAnalysis::isSpace(char c) {

    return c == ' ' || c == '\t';
}

bool LexicalAnalysis::isLineSpace(char c) {

    return c == '\n' || file.eof();
}

bool LexicalAnalysis::isNumber(char c) {

    return '0' <= c && c <= '9';
}

bool LexicalAnalysis::isAllNumber(string s) {

    for ( char i : s )

        if ( ! isNumber( i ) ) return false;

    return true;
}

bool LexicalAnalysis::isRealNumber(string s) {

    bool isReal = false;
    bool haveNum = false;

    for ( char c : s ) {

        if ( isNumber(c) ) {

            if ( isReal && haveNum ) haveNum = false;
        }
        else {

            if ( ! isReal ) {

                if ( c == '.' ) {

                    isReal = true;
                    haveNum = true;
                }
                else return false;


            }
            else return false;
        }

    }

    if ( isReal && ! haveNum ) return true;
    else return false;
}

bool LexicalAnalysis::readInput(string dataName) {

    file.open( dataName, ios::in );

    if ( ! file ) {

        cout << dataName << " not open !" << endl;

        return false;
    }

    char c;
    string buffer = "";
    string lineBuffer = "";
    int lineNumber = 0;

    bool inLine = false;

    Line *line;
    Token token;

    while ( ! file.eof() ) {

        if ( ! inLine ) {

            lineNumber ++;

            line = new Line();
            line->lineNumber = lineNumber;

            inLine = true;
        }

        file.get(c);

        if ( isLineSpace(c) );
        else if ( isSpace(c) ) {

            lineBuffer.push_back(c);

            if ( ! buffer.empty() ) {

                token = createToken( buffer );
                line->tokenList.push_back( token );

                buffer = "";
            }
        }
        else if ( delimiterTable->isInTable(string( 1, c ) ) ) {

            lineBuffer.push_back(c);

            if ( ! buffer.empty() ) {

                token = createToken( buffer );
                line->tokenList.push_back( token );
                buffer = "";
            }

            token = createToken( string( 1, c ) );
            line->tokenList.push_back( token );
            buffer = "";
        }
        else {

            buffer.push_back(c);
            lineBuffer.push_back(c);
        }


        if ( c == '\n' || file.eof() ) {

            inLine = false;

            if ( ! buffer.empty() ) {
                token = createToken( buffer );
                line->tokenList.push_back( token );
            }

            line->readString = lineBuffer;

            lineBuffer = "";
            buffer = "";

            lineList.push_back( line );
        }
    }

    file.close();

    return true;
}

Token LexicalAnalysis::createToken( string s ) {

    string s_large, s_small;

    for ( char c : s ) {

        if ( 65 <= c && c <= 90 ) {

            s_small.push_back( (char)(c + 32) );
            s_large.push_back(c);

        }
        else if ( 97 <= c && c <= 122 ) {

            s_large.push_back( (char)(c - 32));
            s_small.push_back(c);
        }
        else {
            s_large.push_back(c);
            s_small.push_back(c);
        }
    }

    // cout << s_small << " / " << s_large << endl;

    Token *token = new Token();


    if ( delimiterTable->isInTable(s) ) {

        token->s = s;
        token->tableNum = 1;
        token->position = delimiterTable->getPosition(s);
    }
    else if ( instructionTable->isInTable(s_large) ) {

        token->s = s;
        token->tableNum = 2;
        token->position = instructionTable->getPosition(s_large);
    }
    else if ( isAllNumber(s) ) {

        token->s = s;
        token->tableNum = 3;
        integerTable->addSymbol(token->s, true);
        token->position = integerTable->getPosition(token->s, -1);
    }
    else if ( isRealNumber(s) ) {

        token->s = s;
        token->tableNum = 4;
        realNumberTable->addSymbol(token->s, true);
        token->position = realNumberTable->getPosition(token->s, -1);
    }
    else {

        token->s = s;
        token->tableNum = 5;
        symbolTable->addSymbol(s, true);
        token->position = symbolTable->getPosition(s, -1);
    }

    // cout << token->s << " / " << token->tableNum << " / " << token->position << endl << endl;

    return *token;
}

void LexicalAnalysis::printAns( string fileName ) {

    //file.open( fileName, ios::out );

    for ( Line *line : lineList ) {

        cout << line->readString << endl;
        //file << line->readString << endl;

        for ( Token t : line->tokenList ) {

            cout << "(" << t.tableNum << "," << t.position << ")";
            //file << "(" << t.tableNum << "," << t.position << ")";
        }

        cout << endl;
        //file << endl;
    }
}

SyntaxAnalysis *LexicalAnalysis::createSyntaxAnalysis() {

    return new SyntaxAnalysis( lineList, integerTable);
}


class WorkSpace{

public:
    WorkSpace();

    bool lexicalAnalysis( string fileName );
    void syntaxAnalysis();


private:

    string fileName;
    LexicalAnalysis *lexicalAnalysisWorkspace{};
    SyntaxAnalysis *syntaxAnalysisWorkspace;

};

WorkSpace::WorkSpace() = default;

bool WorkSpace::lexicalAnalysis( string fileName ) {

    string outputFileName;

    lexicalAnalysisWorkspace = new LexicalAnalysis(100);

    if ( ! lexicalAnalysisWorkspace->readInput(fileName) ) return false;

    this->fileName = fileName;

    cout << endl << "Get Token Finish !" << endl << endl;

    cout << "Enter 'print' to print lexical analysis result, enter other not print : ";
    cin >> outputFileName;

    if ( outputFileName == "print" ) {

        cout << endl;
        cout << "Enter output file name:";
        cin >> outputFileName;
        cout << endl << endl;

        lexicalAnalysisWorkspace->printAns(outputFileName);
    }

    return true;
}

void WorkSpace::syntaxAnalysis() {

    syntaxAnalysisWorkspace = lexicalAnalysisWorkspace->createSyntaxAnalysis();

    syntaxAnalysisWorkspace->start();
    syntaxAnalysisWorkspace->printAns();
}


int main() {

    string fileName;

    WorkSpace *workSpace = new WorkSpace();

    while (true) {

        cout << "Enter input file name:";
        cin >> fileName;

        if ( workSpace->lexicalAnalysis(fileName) ) break;
    }

    workSpace->syntaxAnalysis();
}
