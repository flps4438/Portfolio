package PL109_10727242;

import CYICE.*;
import java.util.Vector;

class Type {
  public static final int SYMBOL = 0;
  public static final int INT = 1;
  public static final int FLOAT = 2;
  public static final int STRING = 3;
  public static final int NIL = 4;
  public static final int T = 5;
  public static final int LEFT_PAREN = 6;
  public static final int RIGHT_PAREN = 7;
  public static final int DOT = 8;
  public static final int QUOTE = 9;

  public static final int FUNCTION = 10; // else use
  public static final int LAMBDA = 11; // prelambda
  public static final int IERROR = 12; // read error ( use in read instruction Project 1 )
  public static final int ERROROBJ = 13; // error object
} // class Type

class FunctionType {
  public static final int CONS = 1;
  public static final int LIST = 2;
  public static final int QUOTE = 3;
  public static final int DEFINE = 4;
  public static final int CAR = 5;
  public static final int CDR = 6;
  public static final int ISATOM = 7;
  public static final int ISPAIR = 8;
  public static final int ISLIST = 9;
  public static final int ISNULL = 10;
  public static final int ISINTEGER = 11;
  public static final int ISREAL = 12;
  public static final int ISNUMBER = 13;
  public static final int ISSTRING = 14;
  public static final int ISBOOLEAN = 15;
  public static final int ISSYMBOL = 16;
  public static final int ADD = 17;
  public static final int SUB = 18;
  public static final int MUT = 19;
  public static final int DIV = 20;
  public static final int NOT = 21;
  public static final int AND = 22;
  public static final int OR = 23;
  public static final int BIGGER = 24;
  public static final int BEQ = 25;
  public static final int SMALLER = 26;
  public static final int SEQ = 27;
  public static final int EQUAL = 28;
  public static final int STRAPPEND = 29;
  public static final int ISSTRBIGGER = 30;
  public static final int ISSTRSMALLER = 31;
  public static final int ISSTREQUAL = 32;
  public static final int ISEQV = 33;
  public static final int ISEQUAL = 34;
  public static final int BEGIN = 35;
  public static final int IF = 36;
  public static final int COND = 37;
  public static final int ELSE = 38;
  public static final int CLEAN = 39;
  public static final int EXIT = 40;

  public static final int FUNCTION = 41;
  public static final int LET = 42;
  public static final int PRELAMBDA = 43;
  public static final int LAMBDA = 44;
  public static final int VERBOSE = 45;
  public static final int VERSTATUS = 46;

  public static final int CREATEERROR = 47;
  public static final int ISERROROBJ = 48;
  public static final int READ = 49;
  public static final int WRITE = 50;
  public static final int DISPLAYSTR = 51;
  public static final int NEWLINE = 52;
  public static final int EVAL = 53;
  public static final int SET = 54;
  public static final int SYMBOLTOSTR = 55;
  public static final int NUMBERTOSTR = 56;
} // class FunctionType

class Token {

  private String mName;
  private int mLine;
  private int mPosition;
  private int mType;

  public Token() {
  } // Token()

  public Token( String mName, int mType ) {

    this.mName = mName;
    this.mType = mType;

    mLine = -1;
    mPosition = -1;
  } // Token()

  public String GetmName() {
    return mName;
  } // GetmName()

  public void SetmName( String mName ) {
    this.mName = mName;
  } // SetmName()

  public int GetmLine() { return mLine; } // GetmLine()

  public void SetmLine( int mLine ) { this.mLine = mLine; } // SetmLine()

  public int GetmPosition() {
    return mPosition;
  } // GetmPosition()

  public void SetmPosition( int mPosition ) {
    this.mPosition = mPosition;
  } // SetmPosition()

  public int GetmType() {
    return mType;
  } // GetmType()

  public void SetmType( int mType ) {
    this.mType = mType;
  } // SetmType()
} // class Token

class TreeNode {

  private Token mToken = null;
  private TreeNode mLeftNode = null;
  private TreeNode mRightNode = null;
  private TreeNode mTopNode = null;
  private boolean mEval = false;

  public Token GetmToken() { return mToken; } // GetmToken()

  public void SetmToken( Token mToken ) {
    this.mToken = mToken;
  } // SetmToken()

  public TreeNode GetmTopNode() {
    return mTopNode;
  } // GetmTopNode()

  public void SetmTopNode( TreeNode mTopNode ) {
    this.mTopNode = mTopNode;
  } // SetmTopNode()

  public TreeNode GetmLeftNode() {
    return mLeftNode;
  } // GetmLeftNode()

  public void SetmLeftNode( TreeNode mLeftNode ) {
    this.mLeftNode = mLeftNode;
  } // SetmLeftNode()

  public TreeNode GetmRightNode() {
    return mRightNode;
  } // GetmRightNode()

  public void SetmRightNode( TreeNode mRightNode ) {
    this.mRightNode = mRightNode;
  } // SetmRightNode()

  public boolean GetEval() { return mEval; } // GetEval()

  public void SetEval() { mEval = true; } // SetEval()

  public void SetEval( boolean eval ) { mEval = eval; } // SetEval()

  public boolean IsTreeNode() {

    return mToken == null && ( mLeftNode != null || mRightNode != null );

  } // IsTreeNode()

  public boolean IsTreeLeaf() {

    return mToken != null && mLeftNode == null && mRightNode == null;

  } // IsTreeLeaf()

  public boolean IsTreeRoot() {

    return ! HaveTop();

  } // IsTreeRoot()

  public boolean HaveTop() {

    return mTopNode != null;

  } // HaveTop()

  public boolean HaveLeft() {

    return mLeftNode != null;

  } // HaveLeft()

  public boolean HaveRight() {

    return mRightNode != null;

  } // HaveRight()

  /**
  * @param control = 1 left, 2 right, 0 top, other null
  * @return until deep place, or other null
  */

  public TreeNode GetDeep( int control ) {

    TreeNode treeNode;

    if ( control == 0 ) {

      treeNode = mTopNode;

      while ( treeNode != null && treeNode.GetmTopNode() != null )

        treeNode = treeNode.GetmTopNode();

      return treeNode;
    } // if
    else if ( control == 1 ) {

      treeNode = mLeftNode;

      while ( treeNode != null && treeNode.GetmLeftNode() != null )

        treeNode = treeNode.GetmLeftNode();

      return treeNode;
    } // else if
    else if ( control == 2 ) {

      treeNode = mRightNode;

      while ( treeNode != null && treeNode.GetmRightNode() != null )

        treeNode = treeNode.GetmRightNode();

      return treeNode;
    } // else if

    return null;
  } // GetDeep()

} // class TreeNode


class VariableNode {

  private String mName;
  private TreeNode mNodePointer;

  public VariableNode( String name, TreeNode nodePointer ) {

    this.mName = name;
    this.mNodePointer = nodePointer;
  } // VariableNode()

  public String GetName() {
    return mName;
  } // GetName()

  public void SetName( String name ) {
    this.mName = name;
  } // SetName()

  public TreeNode GetNodePointer() {
    return mNodePointer;
  } // GetNodePointer()

  public void SetNodePointer( TreeNode nodePointer ) {
    this.mNodePointer = nodePointer;
  } // SetNodePointer()

} // class VariableNode

class VariableList {

  private Vector<VariableNode> mVariableList = new Vector<VariableNode>();
  private int mGlobalTop = -1;
  private int mFunctionBottom = -1;
  private Vector<Integer> mRestoreFunctionBottom = new Vector<Integer>();

  public Vector<VariableNode> GetVariableList() {

    return mVariableList;
  } // GetVariableList()

  public int GetVariableListSize() {

    return mVariableList.size();

  } // GetVariableListSize()

  public void ClearVariableList() {

    mVariableList.clear();
    mFunctionBottom = -1;
    mGlobalTop = -1;
    mRestoreFunctionBottom.clear();

  } // ClearVariableList()

  public int AddIntoVariableList( VariableNode addNode, boolean fun ) {

    mVariableList.add( addNode );

    if ( ! fun ) mGlobalTop = mVariableList.size() - 1;

    return mVariableList.size() - 1;

  } // AddIntoVariableList()

  public int AddIntoVariableList( VariableNode addNode ) {

    mVariableList.add( addNode );

    mGlobalTop = mVariableList.size() - 1;

    return mVariableList.size() - 1;

  } // AddIntoVariableList()

  public VariableNode FindInVariableList( String name ) {

    for ( int i = mVariableList.size() - 1 ; i >= 0  ; i -- ) {

      if ( i >= mFunctionBottom || i <= mGlobalTop ) {

        VariableNode node = mVariableList.elementAt( i );

        if ( node.GetName().equals( name ) ) return node;
      } // if
    } // for

    return null;

  } // FindInVariableList()

  public VariableNode FindInVariableList( int position ) {

    if ( position >= mVariableList.size() ) return null;
    else if ( position >= mFunctionBottom || position <= mGlobalTop )

      return mVariableList.elementAt( position );

    else

      return null;

  } // FindInVariableList()

  public int FindPositionInList( String name ) {

    for ( int i = mVariableList.size() - 1 ; i >= 0  ; i -- ) {

      if ( i >= mFunctionBottom || i <= mGlobalTop ) {

        VariableNode node = mVariableList.elementAt( i );

        if ( node.GetName().equals( name ) ) return i;
      } // if
    } // for

    return -1;

  } // FindPositionInList()

  public boolean IsInVariableList( String name ) {

    return FindInVariableList( name ) != null;

  } // IsInVariableList()

  public void SetFunctionBottom() {

    mRestoreFunctionBottom.add( new Integer( mFunctionBottom ) );

    mFunctionBottom = mVariableList.size();

  } // SetFunctionBottom()

  public void RestoreFunctionBottom() {

    mFunctionBottom = mRestoreFunctionBottom.elementAt( mRestoreFunctionBottom.size() - 1 );

    mRestoreFunctionBottom.remove( mRestoreFunctionBottom.size() - 1 );

  } // RestoreFunctionBottom()

  public boolean RemoveFromVariableList( int pos ) {

    if ( pos >= mVariableList.size() ) return false;
    else if ( pos >= mFunctionBottom  ) {

      mVariableList.remove( pos );

      for ( int i = 0 ; i < mRestoreFunctionBottom.size() ; i ++ ) {

        if ( mRestoreFunctionBottom.elementAt( i ) >= pos )

          mRestoreFunctionBottom.set( i, mRestoreFunctionBottom.elementAt( i ) - 1 );
      } // for

    } // else if
    else if ( pos <= mGlobalTop ) {

      for ( int i = 0 ; i < mRestoreFunctionBottom.size() ; i ++ ) {

        if ( mRestoreFunctionBottom.elementAt( i ) >= pos )

          mRestoreFunctionBottom.set( i, mRestoreFunctionBottom.elementAt( i ) - 1 );
      } // for

      mVariableList.remove( pos );

      mGlobalTop --;
      mFunctionBottom --;
    } // else if
    else return false;

    return true;

  } // RemoveFromVariableList()

  public boolean EditItemInVariableList( VariableNode newNode ) {

    if ( ! IsInVariableList( newNode.GetName() ) ) return false;
    else {

      int pos = FindPositionInList( newNode.GetName() );

      mVariableList.remove( pos );
      mVariableList.insertElementAt( newNode, pos );

      return true;

    } // else

  } // EditItemInVariableList()

  public Vector<Integer> ConnectVariableList( VariableList variableList ) {

    Vector<Integer> insertPosition = new Vector<Integer>();

    Vector<VariableNode> v = variableList.GetVariableList();

    SetFunctionBottom();

    for ( int i = 0 ; i < v.size() ; i ++ ) {

      insertPosition.add( new Integer( AddIntoVariableList( v.elementAt( i ), true ) ) );

      // if ( i == 0 ) mFunctionBottom = mVariableList.size();
    } // for

    return insertPosition;
  } // ConnectVariableList()

  public Vector<Integer> ConnectVariableList( VariableList variableList, boolean fun ) {

    Vector<Integer> insertPosition = new Vector<Integer>();

    Vector<VariableNode> v = variableList.GetVariableList();

    /*
    if ( fun || mFunctionBottom != -1 )

      SetFunctionButtom();
    */

    for ( int i = 0 ; i < v.size() ; i ++ ) {

      boolean cond = ( fun || mFunctionBottom != -1 );

      insertPosition.add( new Integer( AddIntoVariableList( v.elementAt( i ), cond ) ) );

      // if ( i == 0 ) mFunctionBottom = mVariableList.size();
    } // for

    return insertPosition;
  } // ConnectVariableList()

  public void RemoveVariableUseList( Vector<Integer> posList ) {

    int count = 0;

    for ( int i = 0 ; i < posList.size() ; i ++, count ++ )

      RemoveFromVariableList( posList.elementAt( i - count ).intValue() );

    RestoreFunctionBottom();

  } // RemoveVariableUseList()

  public void RemoveVariableUseList( Vector<Integer> posList, boolean restore ) {

    int count = 0;

    for ( int i = 0 ; i < posList.size() ; i ++, count ++ )

      RemoveFromVariableList( posList.elementAt( i - count ).intValue() );

    if ( restore )

      RestoreFunctionBottom();

  } // RemoveVariableUseList()

} // class VariableList


class FunctionNode {

  private String mName;
  private TreeNode mNodePointer;
  private VariableList mVariableList;
  private Vector<TreeNode> mInstructionList = new Vector<TreeNode>();

  public FunctionNode( String mName, TreeNode mNodePointer,
                       VariableList mVariableList, Vector<TreeNode> mInstructionList ) {
    this.mName = mName;
    this.mNodePointer = mNodePointer;
    this.mVariableList = mVariableList;
    this.mInstructionList = mInstructionList;
  } // FunctionNode()

  public FunctionNode( String mName, TreeNode mNodePointer ) {
    this.mName = mName;
    this.mNodePointer = mNodePointer;
  } // FunctionNode()

  public String GetmName() {
    return mName;
  } // GetmName()

  public TreeNode GetmNodePointer() {
    return mNodePointer;
  } // GetmNodePointer()

  public void SetmNodePointer( TreeNode mNodePointer ) {
    this.mNodePointer = mNodePointer;
  } // SetmNodePointer()

  public VariableList GetmVariableList() {
    return mVariableList;
  } // GetmVariableList()

  public void SetmVariableList( VariableList mVariableList ) {
    this.mVariableList = mVariableList;
  } // SetmVariableList()

  public Vector<TreeNode> GetmInstructionList() {
    return mInstructionList;
  } // GetmInstructionList()

  public void SetmInstructionList( Vector<TreeNode> mInstructionList ) {
    this.mInstructionList = mInstructionList;
  } // SetmInstructionList()
} // class FunctionNode

class FunctionList {

  Vector<FunctionNode> mFunctionList = new Vector<FunctionNode>();

  public Vector<FunctionNode> GetmFunctionList() {

    return mFunctionList;
  } // GetmFunctionList()

  public void ClearFunctionList() {

    mFunctionList.clear();
  } // ClearFunctionList()

  public int AddIntoFunctionList( FunctionNode node ) {

    mFunctionList.add( node );

    return mFunctionList.size() - 1;
  } // AddIntoFunctionList()

  public FunctionNode FindInFunctionList( String name ) {

    for ( int i = 0 ; i < mFunctionList.size() ; i ++ ) {

      FunctionNode node = mFunctionList.elementAt( i );

      if ( node.GetmName().equals( name ) ) return node;

    } // for

    return null;

  } // FindInFunctionList()

  public FunctionNode FindInFunctionList( int position ) {

    if ( position >= mFunctionList.size() ) return null;
    else

      return mFunctionList.elementAt( position );

  } // FindInFunctionList()

  public int FindPositionInList( String name ) {

    for ( int i = 0 ; i < mFunctionList.size() ; i ++ ) {

      FunctionNode node = mFunctionList.elementAt( i );

      if ( node.GetmName().equals( name ) ) return i;

    } // for

    return -1;

  } // FindPositionInList()

  public boolean IsInFunctionList( String name ) {

    return FindInFunctionList( name ) != null;

  } // IsInFunctionList()

  public boolean RemoveFromFunctionList( String name ) {

    if ( IsInFunctionList( name ) )

      mFunctionList.remove( FindInFunctionList( name ) );

    else return false;

    return true;

  } // RemoveFromFunctionList()

  public boolean RemoveFromFunctionList( int pos ) {

    mFunctionList.remove( pos );

    return true;

  } // RemoveFromFunctionList()

  public boolean EditItemInFunctionList( FunctionNode newNode ) {

    if ( ! IsInFunctionList( newNode.GetmName() ) ) return false;
    else {

      int pos = FindPositionInList( newNode.GetmName() );

      mFunctionList.remove( pos );
      mFunctionList.insertElementAt( newNode, pos );

      return true;

    } // else

  } // EditItemInFunctionList()
} // class FunctionList


class GetToken {

  private ICEInputStream mInputStream = null;
  private int mLineNumber = 1;
  private int mColumnNumber = 1;
  private char mReadBuffer = '\u0000';
  private boolean mSkip = true;
  private boolean mEofError = false;

  public GetToken( ICEInputStream inputStream ) {

    this.mInputStream = inputStream;

  } // GetToken()

  public void ResetCount() throws Throwable {

    if ( IsLineSpace( mReadBuffer ) ) GetNextChar();

    mLineNumber = 1;

    if ( mSkip ) {

      mColumnNumber = 0;

    } // if
    else mSkip = true;

  } // ResetCount()

  public void ResetCountAt( int columnNumber ) throws Throwable {

    if ( IsLineSpace( PeekNextChar() ) ) GetNextChar();

    mLineNumber = 1;
    mColumnNumber = columnNumber;
  } // ResetCountAt()


  private char GetNextChar() throws Throwable {

    if ( mInputStream.AtEOF() ) {

      mEofError = true;

      throw new Exception( "ERROR (no more input) : END-OF-FILE encountered" );
    } // if
    else {

      char c;

      if ( mReadBuffer != '\u0000' ) {

        c = mReadBuffer;

        mReadBuffer = '\u0000';

      } // if
      else c = mInputStream.ReadChar();

      if ( IsLineSpace( c ) ) {

        mLineNumber ++;
        mColumnNumber = 0;
      } // if
      else mColumnNumber ++;

      return c;

    } // else
  } // GetNextChar()

  private char PeekNextChar() throws Throwable {

    char c;

    if ( mReadBuffer != '\u0000' )

      c = mReadBuffer;

    else {

      c = mInputStream.ReadChar();

      mReadBuffer = c;

    } // else

    return c;

  } // PeekNextChar()

  public Token GetNextToken() throws Throwable {

    StringBuilder readInput = new StringBuilder();

    char c = PeekNextChar();

    boolean read = true;
    boolean readString = false;
    boolean comment = false;

    Token token;

    while ( read && ! mInputStream.AtEOF() ) {

      c = GetNextChar();

      if ( readString ) {

        readInput.append( c );

        if ( c == '"' && readInput.toString().charAt( readInput.length() - 2 ) != '\\' ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          return token;
        } // if
      } // if
      else if ( IsLineSpace( c ) ) {

        if ( ! readInput.toString().isEmpty() ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          return token;
        } // if

        comment = false;
      } // else if
      else if ( comment ) ;
      else if ( IsSpace( c ) ) {

        if ( ! readInput.toString().isEmpty() ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          return token;
        } // if
      } // else if
      else if ( c == '(' || c == ')' || c == '\'' ) {

        if ( ! readInput.toString().isEmpty() ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          mReadBuffer = c;

          mColumnNumber --;

          return token;
        } // if

        readInput.append( c );

        token = CreateToken( readInput.toString(), mColumnNumber );

        return token;
      } // else if
      else if ( c == '"' ) {

        if ( ! readInput.toString().isEmpty() ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          mReadBuffer = c;

          mColumnNumber --;

          return token;
        } // if

        readInput.append( c );

        readString = true;
      } // else if
      else if ( c == ';' ) {

        if ( ! readInput.toString().isEmpty() ) {

          token = CreateToken( readInput.toString(), mColumnNumber - 1 );

          mReadBuffer = c;

          mColumnNumber --;

          return token;
        } // if

        comment = true;
      } // else if
      else readInput.append( c );

      c = PeekNextChar();

      if ( readString ) {

        if ( IsLineSpace( c ) )

          throw new Exception( "ERROR (no closing quote) : END-OF-LINE encountered at Line "
                               + mLineNumber + " Column " +  ( mColumnNumber + 1 ) );
      } // if
      else if ( comment && IsLineSpace( c ) ) {

        comment = false;

        c = GetNextChar();
      } // else if
      else if ( comment ) ;
      else if ( ( IsSpace( c ) || IsLineSpace( c ) ) ) read = false;

    } // while

    if ( mInputStream.AtEOF() ) {

      mEofError = true;

      throw new Exception( "ERROR (no more input) : END-OF-FILE encountered" );
    } // if
    else if ( ! readInput.toString().isEmpty() ) {

      token = CreateToken( readInput.toString(), mColumnNumber  );

      return token;
    } // if
    else return GetNextToken();

  } // GetNextToken()

  private Token CreateToken( String readInput, int columnNumber ) {

    Token t = new Token();

    t.SetmPosition( columnNumber - readInput.length() + 1 );
    t.SetmLine( mLineNumber );
    t.SetmName( readInput );

    if ( readInput.equals( "(" ) )

      t.SetmType( Type.LEFT_PAREN );

    else if ( readInput.equals( ")" ) )

      t.SetmType( Type.RIGHT_PAREN );

    else if ( readInput.equals( "." ) )

      t.SetmType( Type.DOT );

    else if ( IsInt( readInput ) ) {

      t.SetmType( Type.INT );

      if ( readInput.charAt( 0 ) == '+' ) t.SetmName( readInput.substring( 1 ) );

    } // else if
    else if ( IsFloat( readInput ) ) {

      t.SetmType( Type.FLOAT );

      String underPoint = readInput.substring( readInput.indexOf( "." ) + 1 );

      if ( underPoint.equals( "" ) )

        readInput += "000";

      else if ( underPoint.length() == 1 )

        readInput += "00";

      else if ( underPoint.length() == 2 )

        readInput += "0";

      if ( readInput.charAt( 0 ) == '+' ) t.SetmName( readInput.substring( 1 ) );
      else t.SetmName( readInput );
    } // else if
    else if ( readInput.charAt( 0 ) == '"' && readInput.charAt( readInput.length() - 1 ) == '"' )

      t.SetmType( Type.STRING );

    else if ( readInput.equals( "'" )  ) // || readInput.equals( "quote" )

      t.SetmType( Type.QUOTE );

    else if ( readInput.equals( "nil" ) || readInput.equals( "#f" ) || readInput.equals( "()" ) )

      t.SetmType( Type.NIL );

    else if ( readInput.equals( "t" ) || readInput.equals( "#t" ) )

      t.SetmType( Type.T );

    else if ( IsSymbol( readInput ) )

      t.SetmType( Type.SYMBOL );

    else {

      t.SetmType( Type.IERROR );
      System.out.println( "ERROR! Token can't find type. >> " + readInput + " << " );
    } // else

    return t;

  } // CreateToken()

  private boolean IsSpace( char c ) {

    return c == ' ' || c == '\t';
  } // IsSpace()

  private boolean IsLineSpace( char c ) {

    return c == '\n';
  } // IsLineSpace()

  private boolean IsNumber( char c ) {

    return '0' > c || c > '9';
  } // IsNumber()

  private boolean IsInt( String input ) {

    int start = 0;
    boolean haveNumber = false;

    if ( input.charAt( 0 ) == '+' || input.charAt( 0 ) == '-' ) start = 1;

    for ( int i = start ; i < input.length() ; i ++ ) {

      char c = input.charAt( i );

      if ( IsNumber( c ) ) return false;
      else haveNumber = true;

    } // for

    return haveNumber;
  } // IsInt()

  private boolean IsFloat( String input ) {

    int start = 0;
    boolean dot = false;
    boolean haveNumber = false;

    if ( input.charAt( 0 ) == '+' || input.charAt( 0 ) == '-' ) start = 1;

    for ( int i = start ; i < input.length() ; i ++ ) {

      char c = input.charAt( i );

      if ( IsNumber( c ) && ( c != '.' || dot ) ) return false;
      else if ( c == '.' && ! dot ) dot = true;
      else haveNumber = true;
    } // for

    return haveNumber;
  } // IsFloat()

  private boolean IsSymbol( String input ) {

    for ( int i = 0 ; i < input.length() ; i ++ ) {

      char c = input.charAt( i );

      if ( IsSpace( c ) || c == '(' || c == ')' || c == '\'' || c == '"' ) return false;

    } // for

    return true;
  } // IsSymbol()

  public boolean IsAtEOF() throws Throwable {

    return mInputStream.AtEOF() && mReadBuffer == '\u0000';
  } // IsAtEOF()

  public void SkipLine() throws Throwable {

    // System.out.println( " C0 : " + "MRB = '" + mReadBuffer + "'" );

    if ( mInputStream.AtEOF() ) return;

    if ( mReadBuffer == '\u0000' )

      PeekNextChar();

    char c = GetNextChar();

    if ( IsLineSpace( c ) ) {

      mReadBuffer = '\u0000';

      return;
    } // if

    while ( ! IsLineSpace( c ) ) c = GetNextChar();

    // System.out.println( "C2: '" + c + "'" );

    mReadBuffer = GetNextChar();
  } // SkipLine()

  public void TrySkipLine() throws Throwable {

    mLineNumber = 1;
    mColumnNumber = 0;

    if ( mReadBuffer == '\u0000' )

      PeekNextChar();

    if ( IsAtEOF() ) return;

    char c = GetNextChar();

    if ( IsLineSpace( c ) ) {

      mReadBuffer = '\u0000';

      return;
    } // if

    while ( IsSpace( c ) ) {

      c = GetNextChar();

    } // while

    if ( c == ';' ) {

      while ( ! IsLineSpace( c ) )

        c = GetNextChar();

    } // if

    if ( IsLineSpace( c ) )

      c = '\u0000';

    else {

      mSkip = false;
      mColumnNumber --;

    } // else

    mReadBuffer = c;

  } // TrySkipLine()

  public boolean GetEofError() {

    return mEofError;
  } // GetEofError()

} // class GetToken

class StatementGet {

  private ICEInputStream mInputStream;
  private GetToken mGetToken;
  private Token mTokenBuffer = null;
  private TreeNode mTree = null;
  private boolean mEofError = false;

  private Vector<Token> mTokenList;

  public StatementGet( ICEInputStream inputStream ) {

    this.mInputStream = inputStream;

    mGetToken = new GetToken( inputStream );
    mTokenList = new Vector<Token>();

  } // StatementGet()

  public Vector<Token> GetStatement() throws Throwable {

    return GetStatement( false );
  } // GetStatement()

  public Vector<Token> GetStatement( boolean read ) throws Throwable {

    mGetToken.ResetCount();
    mTokenList.clear();
    mTree = null;

    Token nowToken;

    try {

      if ( mTokenBuffer != null ) {

        nowToken = GetNextToken();

        nowToken.SetmLine( 1 );
        nowToken.SetmPosition( 1 );

        mGetToken.ResetCountAt( nowToken.GetmName().length() );
      } // if
      else nowToken = GetNextToken();

      mTree = IsStatement( nowToken, mTree );

      if ( mTree != null )

        mGetToken.TrySkipLine();

      else

        System.out.println( "ERROR! Error code : 87" );

    } // try
    catch ( Exception e ) {

      mTokenList.clear();

      mTree = null;

      if ( ! read ) {

        System.out.println( e.getMessage() );

        if ( ! e.getMessage().contains( "END-OF-FILE" ) )

          System.out.println();

        SkipLine();
      } // if
      else throw e;

    } // catch

    return mTokenList;
  } // GetStatement()

  public void SkipLine() throws Throwable {

    mGetToken.SkipLine();

  } // SkipLine()

  private boolean IsEOF() throws Throwable {

    return mGetToken.IsAtEOF() && mTokenBuffer == null;

  } // IsEOF()

  private Token GetNextToken() throws Throwable {

    Token t = null;

    if ( mTokenBuffer != null ) {

      t = mTokenBuffer;

      mTokenBuffer = null;

      return t;
    } // if
    else if ( ! mGetToken.IsAtEOF() )

      return mGetToken.GetNextToken();

    else {

      mEofError = true;

      throw new Exception( "ERROR (no more input) : END-OF-FILE encountered" );
    } // else
  } // GetNextToken()
  
  private TreeNode IsStatement( Token t, TreeNode node ) throws Throwable {

    Token nowToken = t;

    if ( IsATOM( nowToken ) ) {

      if ( nowToken.GetmType() != Type.LEFT_PAREN )

        mTokenList.add( nowToken );

      else nowToken = mTokenList.lastElement();

      if ( node == null ) node = new TreeNode();

      node.SetmToken( nowToken );

      return node;
    } // if
    else if ( nowToken.GetmType() == Type.LEFT_PAREN ) {

      mTokenList.add( nowToken );

      if ( node == null ) node = new TreeNode();

      nowToken = GetNextToken();

      TreeNode temp = IsStatement( nowToken, node.GetmLeftNode() );

      if ( temp != null ) {

        node.SetmLeftNode( temp );
        temp.SetmTopNode( node );
      } // if
      else return null;

      nowToken = GetNextToken();

      if ( IsStatementHead( nowToken ) ) {

        node.SetmRightNode( new TreeNode() );

        TreeNode treeRight = node.GetmRightNode();

        treeRight.SetmTopNode( node );

        temp = IsStatement( nowToken, treeRight.GetmLeftNode() );

        while ( IsStatementHead( nowToken ) && temp != null ) {

          treeRight.SetmLeftNode( temp );
          temp.SetmTopNode( treeRight );

          nowToken = GetNextToken();

          if ( IsStatementHead( nowToken ) ) {

            treeRight.SetmRightNode( new TreeNode() );
            treeRight.GetmRightNode().SetmTopNode( treeRight );

            treeRight = treeRight.GetmRightNode();

            temp = IsStatement( nowToken, treeRight.GetmLeftNode() );
          } // if
        } // while
      } // if

      if ( nowToken.GetmType() == Type.DOT ) {

        mTokenList.add( nowToken );

        nowToken = GetNextToken();

        TreeNode treeRight = node.GetDeep( 2 );

        if ( treeRight != null ) temp = IsStatement( nowToken, treeRight.GetmRightNode() );
        else temp = IsStatement( nowToken, treeRight );

        if ( temp != null ) {

          if ( treeRight != null ) {

            treeRight.SetmRightNode( temp );
            temp.SetmTopNode( treeRight );
          } // if
          else {

            node.SetmRightNode( temp );
            temp.SetmTopNode( node );
          } // else

          nowToken = GetNextToken();
        } // if
        else return null;
      } // if
      else {

        TreeNode deepNode = node.GetDeep( 2 );

        Token nilToken = new Token();
        nilToken.SetmName( "nil" );
        nilToken.SetmType( Type.NIL );
        nilToken.SetmLine( -1 );
        nilToken.SetmPosition( -1 );

        if ( deepNode != null ) {

          deepNode.SetmRightNode( new TreeNode() );
          deepNode.GetmRightNode().SetmTopNode( deepNode );

          deepNode.GetmRightNode().SetmToken( nilToken );
        } // if
        else {

          node.SetmRightNode( new TreeNode() );
          node.GetmRightNode().SetmTopNode( node );

          node.GetmRightNode().SetmToken( nilToken );
        } // else

      } // else

      if ( nowToken.GetmType() == Type.RIGHT_PAREN ) {

        mTokenList.add( nowToken );

        return node;
      } // if
      else throw new Exception( "ERROR (unexpected token) : ')' expected when token at Line " +
                                nowToken.GetmLine() + " Column " + nowToken.GetmPosition() + " is >>" +
                                nowToken.GetmName() + "<<" );

    } // else if
    else if ( nowToken.GetmType() == Type.QUOTE ) {

      mTokenList.add( nowToken );

      if ( node == null ) node = new TreeNode();

      TreeNode temp = new TreeNode();

      temp.SetmToken( nowToken );

      node.SetmLeftNode( temp );
      temp.SetmTopNode( node );

      node.SetmRightNode( new TreeNode() );

      TreeNode rightNode = node.GetmRightNode();

      rightNode.SetmTopNode( node );

      nowToken = GetNextToken();

      temp = IsStatement( nowToken, rightNode.GetmLeftNode() );

      if ( temp != null ) {

        rightNode.SetmLeftNode( temp );
        temp.SetmTopNode( rightNode );

        Token nilToken = new Token();
        nilToken.SetmName( "nil" );
        nilToken.SetmType( Type.NIL );
        nilToken.SetmLine( -1 );
        nilToken.SetmPosition( -1 );

        rightNode.SetmRightNode( new TreeNode() );

        rightNode.GetmRightNode().SetmToken( nilToken );
      } // if

      return node;
    } // else if
    else throw new Exception( "ERROR (unexpected token) : atom or '(' expected when token at Line " +
                              nowToken.GetmLine() + " Column " + nowToken.GetmPosition() + " is >>" +
                              nowToken.GetmName() + "<<" );
  } // IsStatement()

  private boolean IsStatementHead( Token t ) throws Throwable {

    return t.GetmType() == Type.LEFT_PAREN || t.GetmType() == Type.QUOTE || IsATOM( t );
  } // IsStatementHead()

  private boolean IsATOM( Token t ) throws Throwable {

    int type = t.GetmType();

    if ( type == Type.LEFT_PAREN ) {

      Token next = null;

      if ( ! IsEOF() )

        next = mGetToken.GetNextToken();

      else return false;

      if ( next.GetmType() == Type.RIGHT_PAREN ) {

        Token newToken = new Token();

        newToken.SetmLine( t.GetmLine() );
        newToken.SetmPosition( t.GetmPosition() );
        newToken.SetmName( "nil" );
        newToken.SetmType( Type.NIL );

        mTokenList.add( newToken );

        return true;
      } // if
      else {
        mTokenBuffer = next;
        return false;
      } // else
    } // if
    else
      return  type == Type.SYMBOL || type == Type.INT ||
              type == Type.FLOAT || type == Type.STRING ||
              type == Type.NIL || type == Type.T ;
  } // IsATOM()

  public TreeNode GetTree() {

    return mTree;
  } // GetTree()

  public boolean GetEofError() {

    if ( mGetToken.GetEofError() ) return true;

    return mEofError;
  } // GetEofError()

} // class StatementGet

class Printer {

  private boolean mAfterParen = false;
  private int mCount;
  private Vector<Integer> mCorrectionList;
  private String mPrintString;
  private boolean mPrint;
  private Processer mProcesser;

  public Printer( Processer processer ) {

    mAfterParen = false;
    mCount = 0;
    mCorrectionList = new Vector<Integer>();
    mPrintString = "";
    mPrint = false;
    mProcesser = processer;

  } // Printer()

  public String PrintTree( TreeNode treeNode, boolean print ) {

    mAfterParen = false;
    mCount = 0;
    mCorrectionList = new Vector<Integer>();
    mPrintString = "";
    mPrint = print;

    Print( treeNode );

    return mPrintString;
  } // PrintTree()

  private void Print( TreeNode treeNode ) {

    if ( treeNode.HaveLeft() ) {

      if ( mCorrectionList.isEmpty() ) {

        if ( ! mAfterParen )

          PrintWhiteSpace( mCount );

        if ( mPrint )

          System.out.print( "( " );

        mPrintString += "( ";

        mCount += 2;
        mAfterParen = true;
      } // if
      else {

        int temp = mCorrectionList.elementAt( mCorrectionList.size() - 1 );

        temp -= 1;

        mCorrectionList.remove( mCorrectionList.size() - 1 );

        if ( temp != 0 )

          mCorrectionList.add( new Integer( temp ) );
      } // else

      Print( treeNode.GetmLeftNode() );
    } // if

    if ( treeNode.IsTreeLeaf() ) {

      if ( ! mAfterParen )

        PrintWhiteSpace( mCount );

      else mAfterParen = false;

      Token t = treeNode.GetmToken();

      if ( t.GetmType() == Type.FLOAT ) {

        if ( mPrint )

          System.out.println( FloatString( t.GetmName() ) );

        // mPrintString += String.format( "%.3f", Float.parseFloat( t.GetmName() ) );
        mPrintString += FloatString( t.GetmName() );
        mPrintString += "\n";
      } // if
      else if ( t.GetmType() == Type.INT ) {

        if ( mPrint )

          System.out.println( Integer.parseInt( t.GetmName() ) );

        mPrintString += t.GetmName();
        mPrintString += "\n";
      } // else if
      else if ( t.GetmType() == Type.T ) {

        if ( mPrint )

          System.out.println( "#t" );

        mPrintString += "#t\n";
      } // else if
      else if ( t.GetmType() == Type.NIL ) {

        if ( mPrint )

          System.out.println( "nil" );

        mPrintString += "nil\n";
      } // else if
      else if ( t.GetmType() == Type.QUOTE ) {

        if ( mPrint )

          System.out.println( "quote" );

        mPrintString += "quote\n";
      } // else if
      else if ( t.GetmType() == Type.STRING ) {

        String s = t.GetmName();

        for ( int z = 0 ; z < s.length() ; z++ ) {

          if ( s.charAt( z ) == '\\' ) {

            if ( z + 1 < s.length() && s.charAt( z + 1 ) == '\\' ) {

              String newS = s.substring( 0, z );
              newS += "\\";

              if ( z + 2 < s.length() )

                newS += s.substring( z + 2 );

              s = newS;
            } // if
            else if ( z + 1 < s.length() && s.charAt( z + 1 ) == '\"' ) {

              String newS = s.substring( 0, z );
              newS += "\"";

              if ( z + 2 < s.length() )

                newS += s.substring( z + 2 );

              s = newS;
            } // else if
            else if ( z + 1 < s.length() && s.charAt( z + 1 ) == 'n' ) {

              String newS = s.substring( 0, z );
              newS += "\n";

              if ( z + 2 < s.length() )

                newS += s.substring( z + 2 );

              s = newS;
            } // else if
            else if ( z + 1 < s.length() && s.charAt( z + 1 ) == 't' ) {

              String newS = s.substring( 0, z );
              newS += "\t";

              if ( z + 2 < s.length() )

                newS += s.substring( z + 2 );

              s = newS;
            } // else if
          } // if
        } // for

        if ( mPrint )

          System.out.println( s );

        mPrintString += s;
        mPrintString += "\n";
      } // else if
      else {

        if ( mProcesser.IsFunctionName( t.GetmName() ) && treeNode.GetEval()  ) {

          if ( mPrint )

            System.out.println( "#<procedure " + t.GetmName() + ">" );

          mPrintString += "#<procedure ";
          mPrintString += t.GetmName();
          mPrintString += ">\n";
        } // if
        else {

          if ( mPrint )

            System.out.println( t.GetmName() );

          mPrintString += t.GetmName();
          mPrintString += "\n";
        } // else
      } // else
    } // if

    if ( treeNode.HaveRight() && treeNode.GetmRightNode().IsTreeLeaf() ) {

      if ( treeNode.GetmRightNode().GetmToken().GetmType() != Type.NIL ) {

        PrintWhiteSpace( mCount );

        if ( mPrint )

          System.out.println( "." );

        mPrintString += ".\n";

        Print( treeNode.GetmRightNode() );

        mCount -= 2;

        PrintWhiteSpace( mCount );

        if ( mPrint )

          System.out.println( ")" );

        mPrintString += ")\n";
      } // if
      else {

        mCount -= 2;

        PrintWhiteSpace( mCount );

        if ( mPrint )

          System.out.println( ")" );

        mPrintString += ")\n";
      } // else
    } // if
    else if ( treeNode.HaveRight() ) {

      mCorrectionList.add( new Integer( 1 ) );

      Print( treeNode.GetmRightNode() );
    } // else if

    if ( treeNode.IsTreeRoot() && mCount != 0 ) PrintRemainParen( mCount );

  } // Print()

  private void PrintRemainParen( int count ) {

    while ( count != 0 ) {

      count -= 2;

      PrintWhiteSpace( count );

      if ( mPrint )

        System.out.println( ")" );

      mPrintString += ")\n";
    } // while
  } // PrintRemainParen()

  private void PrintWhiteSpace( int count ) {

    while ( count > 0 ) {

      if ( mPrint )

        System.out.print( " " );

      mPrintString += " ";

      count --;
    } // while
  } // PrintWhiteSpace()

  private float Round( float input ) {

    input = ( float ) ( input * 1000.0 );

    if ( input % 1 > 0.5 )

      input = ( float ) ( ( ( float ) ( ( int ) input ) + 1 ) / 1000.0 );

    else input = ( float ) ( ( float ) ( ( int ) input ) / 1000.0 );

    String inputString = String.valueOf( input );

    String underPoint = inputString.substring( inputString.indexOf( "." ) + 1 );

    if ( underPoint.equals( "" ) )

      inputString += "000";

    else if ( underPoint.length() == 1 )

      inputString += "00";

    else if ( underPoint.length() == 2 )

      inputString += "0";

    return Float.parseFloat( inputString );
  } // Round()

  private String FloatString( String inputString ) {

    String upPoint = inputString.substring( 0, inputString.indexOf( "." ) );
    String underPoint = inputString.substring( inputString.indexOf( "." ) + 1 );

    if ( upPoint.equals( "" ) )

      upPoint = "0";

    else if ( upPoint.equals( "-" ) )

      upPoint = "-0";

    if ( underPoint.equals( "" ) )

      underPoint += "000";

    else if ( underPoint.length() == 1 )

      underPoint += "00";

    else if ( underPoint.length() == 2 )

      underPoint += "0";

    if ( underPoint.length() > 3 ) {

      String ans = String.valueOf( Round( Float.parseFloat( "0." + underPoint ) ) );

      underPoint = ans.substring( ans.indexOf( "." ) + 1 );

    } // if

    return upPoint + "." + underPoint;
  } // FloatString()

} // class Printer


class Processer {

  private Vector<TreeNode> mInstructionList;
  private VariableList mVariableList;
  private FunctionList mFunctionList;
  private FunctionList mLambdaFunList;

  private Printer mPrinter;
  private StatementGet mStatementGet;

  boolean mInCond = false;
  boolean mVerbose = true;

  int mLevel = 0;

  public Processer( StatementGet statementGet ) {

    mInstructionList = new Vector<TreeNode>();
    mVariableList = new VariableList();
    mFunctionList = new FunctionList();
    mLambdaFunList = new FunctionList();

    mPrinter = new Printer( this );
    mStatementGet = statementGet;
  } // Processer()

  public void AddInstruction( TreeNode instructionTree ) throws Throwable {

    mLevel = 0;
    mInCond = false;

    try {

      TreeNode result = CheckGrammer( instructionTree );

      mInstructionList.add( instructionTree );

      mPrinter.PrintTree( result, true );

    } // try
    catch ( Exception e ) {

      if ( e.getMessage().contains( "Define exception" ) ) {

        mInstructionList.add( instructionTree );

        if ( mVerbose )

          System.out.println( e.getMessage().substring( e.getMessage().indexOf( "," ) + 1 ) );
      } // if
      else if ( e.getMessage().equals( "Clean-Environment exception" ) )

        mInstructionList.clear();

      else if ( e.getMessage().equals( "Exit exception" ) )

        throw new Exception( "Exit exception" );

      else if ( e.getMessage().startsWith( "REA/" ) ) {

        String errorMsg =  e.getMessage();

        errorMsg = errorMsg.substring( 4, errorMsg.length() - 1 );

        if ( errorMsg.charAt( errorMsg.length() - 1 ) == '\n' )

          System.out.print( errorMsg );

        else

          System.out.println( errorMsg );

      } // else if
      else {

        String errorMsg = e.getMessage();

        if ( errorMsg.charAt( errorMsg.length() - 1 ) == '\n' )

          System.out.print( errorMsg );

        else

          System.out.println( errorMsg );

      } // else
    } // catch

  } // AddInstruction()

  public boolean IsFunctionName( String name ) {

    if ( IsInternalFunction( new Token( name, Type.SYMBOL ), false ) > 0 ) return true;
    else return mFunctionList.IsInFunctionList( name );

  } // IsFunctionName()

  private TreeNode CheckGrammer( TreeNode tree ) throws Throwable {

    mLevel ++;

    if ( tree.IsTreeLeaf() && tree.GetmToken().GetmType() != Type.SYMBOL ) {

      Token cloneToken = tree.GetmToken();

      TreeNode retuenTree = new TreeNode();

      retuenTree.SetmToken( new Token( cloneToken.GetmName(), cloneToken.GetmType() ) );

      retuenTree.SetEval();

      return retuenTree;
    } // if
    else if ( tree.IsTreeLeaf() && tree.GetmToken().GetmType() == Type.SYMBOL ) {

      Token t = tree.GetmToken();

      if ( IsInternalFunction( t, mInCond ) > 0 ) {

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        returnNode.SetmToken( t );

        return returnNode;
      } // if
      else if ( mVariableList.IsInVariableList( t.GetmName() ) )

        return mVariableList.FindInVariableList( t.GetmName() ).GetNodePointer();

      else if ( mFunctionList.IsInFunctionList( t.GetmName() ) ) {

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        returnNode.SetmToken( t );

        return returnNode;
      } // else if
      else

        throw new Exception( "ERROR (unbound symbol) : " + t.GetmName() );
    } // else if
    else {

      if ( tree.GetDeep( 2 ).GetmToken().GetmType() != Type.NIL )

        throw new Exception( "ERROR (non-list) : " + mPrinter.PrintTree( tree, false ) );

      else if ( tree.GetmLeftNode().IsTreeLeaf() ) {

        Token t = tree.GetmLeftNode().GetmToken();

        if ( t.GetmType() != Type.SYMBOL && t.GetmType() != Type.QUOTE )

          throw new Exception( "ERROR (attempt to apply non-function) : " + t.GetmName() );

        else {

          Token oldT = t;

          if ( mVariableList.IsInVariableList( t.GetmName() ) ) {

            TreeNode binding = mVariableList.FindInVariableList( t.GetmName() ).GetNodePointer();

            if ( binding.IsTreeLeaf() ) t = binding.GetmToken();

            if ( ! binding.GetEval() )

              throw new Exception( "ERROR (attempt to apply non-function) : " +
                                   mPrinter.PrintTree( binding, false ) );
          } // if

          if ( mFunctionList.IsInFunctionList( t.GetmName() ) ) {

            return RunFunction( mFunctionList.FindInFunctionList( t.GetmName() ), tree );

          } // if
          else if ( IsInternalFunction( t, mInCond ) > 0 ) {

            if ( mLevel != 1 && ( t.GetmName().equals( "clean-environment" ) ||
                                  t.GetmName().equals( "define" ) || t.GetmName().equals( "exit" ) ) )

              throw new Exception( "ERROR (level of " + t.GetmName().toUpperCase() + ")" );

            else {

              return DoInternalFunction( t, tree );
            } // else

          } // else if
          else {

            if ( mVariableList.IsInVariableList( oldT.GetmName() ) )

              throw new Exception( "ERROR (attempt to apply non-function) : " +
                                   mPrinter.PrintTree( mVariableList.FindInVariableList( oldT.GetmName() )
                                   .GetNodePointer(), false ) );
            else

              throw new Exception( "ERROR (unbound symbol) : " + oldT.GetmName() );
          } // else
        } // else
      } // else if
      else {

        TreeNode result;

        try {

          result = CheckGrammer( tree.GetmLeftNode() );

        } // try
        catch ( Exception e ) {

          if ( e.getMessage().startsWith( "REA/" ) )

            throw new Exception( "ERROR (no return value) : " +
                                 mPrinter.PrintTree( tree.GetmLeftNode(), false ) );

          else throw e;
        } // catch

        Token t = null;

        if ( result.GetEval() && result.IsTreeLeaf() ) {

          t = result.GetmToken();

          if ( IsInternalFunction( t, mInCond ) > 0 )

            return DoInternalFunction( t, tree );

          else

            throw new Exception( "ERROR (attempt to apply non-function) : " +
                                 mPrinter.PrintTree( result, false ) );

        } // if
        else

          throw new Exception( "ERROR (attempt to apply non-function) : " +
                               mPrinter.PrintTree( result, false ) );

      } // else
    } // else

    // throw new Exception( "Error code 1" );
  } // CheckGrammer()

  private int IsInternalFunction( Token t, boolean mInCond ) {

    String name = t.GetmName();

    if ( name.equals( "cons" ) ) return FunctionType.CONS;
    else if ( name.equals( "list" ) ) return FunctionType.LIST;
    else if ( name.equals( "quote" ) || name.equals( "'" ) ) return FunctionType.QUOTE;
    else if ( name.equals( "define" ) ) return FunctionType.DEFINE;
    else if ( name.equals( "car" ) ) return FunctionType.CAR;
    else if ( name.equals( "cdr" ) ) return FunctionType.CDR;
    else if ( name.equals( "atom?" ) ) return FunctionType.ISATOM;
    else if ( name.equals( "pair?" ) ) return FunctionType.ISPAIR;
    else if ( name.equals( "list?" ) ) return FunctionType.ISLIST;
    else if ( name.equals( "null?" ) ) return FunctionType.ISNULL;
    else if ( name.equals( "integer?" ) ) return FunctionType.ISINTEGER;
    else if ( name.equals( "real?" ) ) return FunctionType.ISREAL;
    else if ( name.equals( "number?" ) ) return FunctionType.ISNUMBER;
    else if ( name.equals( "string?" ) ) return FunctionType.ISSTRING;
    else if ( name.equals( "boolean?" ) ) return FunctionType.ISBOOLEAN;
    else if ( name.equals( "symbol?" ) ) return FunctionType.ISSYMBOL;
    else if ( name.equals( "+" ) ) return FunctionType.ADD;
    else if ( name.equals( "-" ) ) return FunctionType.SUB;
    else if ( name.equals( "*" ) ) return FunctionType.MUT;
    else if ( name.equals( "/" ) ) return FunctionType.DIV;
    else if ( name.equals( "not" ) ) return FunctionType.NOT;
    else if ( name.equals( "and" ) ) return FunctionType.AND;
    else if ( name.equals( "or" ) ) return FunctionType.OR;
    else if ( name.equals( ">" ) ) return FunctionType.BIGGER;
    else if ( name.equals( ">=" ) ) return FunctionType.BEQ;
    else if ( name.equals( "<" ) ) return FunctionType.SMALLER;
    else if ( name.equals( "<=" ) ) return FunctionType.SEQ;
    else if ( name.equals( "=" ) ) return FunctionType.EQUAL;
    else if ( name.equals( "string-append" ) ) return FunctionType.STRAPPEND;
    else if ( name.equals( "string>?" ) ) return FunctionType.ISSTRBIGGER;
    else if ( name.equals( "string<?" ) ) return FunctionType.ISSTRSMALLER;
    else if ( name.equals( "string=?" ) ) return FunctionType.ISSTREQUAL;
    else if ( name.equals( "eqv?" ) ) return FunctionType.ISEQV;
    else if ( name.equals( "equal?" ) ) return FunctionType.ISEQUAL;
    else if ( name.equals( "begin" ) ) return FunctionType.BEGIN;
    else if ( name.equals( "if" ) ) return FunctionType.IF;
    else if ( name.equals( "cond" ) ) return FunctionType.COND;
    else if ( mInCond && name.equals( "else" ) ) return FunctionType.ELSE;
    else if ( name.equals( "clean-environment" ) ) return FunctionType.CLEAN;
    else if ( name.equals( "exit" ) ) return FunctionType.EXIT;
    else if ( name.equals( "let" ) ) return FunctionType.LET;
    else if ( name.equals( "lambda" ) && t.GetmType() == Type.SYMBOL ) return FunctionType.PRELAMBDA;
    else if ( name.equals( "lambda" ) && t.GetmType() == Type.LAMBDA ) return FunctionType.LAMBDA;
    else if ( name.equals( "verbose" ) ) return FunctionType.VERBOSE;
    else if ( name.equals( "verbose?" ) ) return FunctionType.VERSTATUS;
    else if ( name.equals( "create-error-object" ) ) return FunctionType.CREATEERROR;
    else if ( name.equals( "error-object?" ) ) return FunctionType.ISERROROBJ;
    else if ( name.equals( "read" ) ) return FunctionType.READ;
    else if ( name.equals( "write" ) ) return FunctionType.WRITE;
    else if ( name.equals( "display-string" ) ) return FunctionType.DISPLAYSTR;
    else if ( name.equals( "newline" ) ) return FunctionType.NEWLINE;
    else if ( name.equals( "eval" ) ) return FunctionType.EVAL;
    else if ( name.equals( "set!" ) ) return FunctionType.SET;
    else if ( name.equals( "symbol->string" ) ) return FunctionType.SYMBOLTOSTR;
    else if ( name.equals( "number->string" ) ) return FunctionType.NUMBERTOSTR;
    else return -1;
  } // IsInternalFunction()

  private TreeNode DoInternalFunction( Token t, TreeNode tree ) throws Throwable {

    String name = t.GetmName();
    int functionType = IsInternalFunction( t, mInCond );

    if ( functionType == FunctionType.CONS ) return Cons( tree );
    else if ( functionType == FunctionType.LIST ) return List( tree );
    else if ( functionType == FunctionType.QUOTE ) return Quote( tree );
    else if ( functionType == FunctionType.DEFINE ) Define( tree );
    else if ( functionType == FunctionType.CAR ) return Car( tree );
    else if ( functionType == FunctionType.CDR ) return Cdr( tree );
    else if ( functionType == FunctionType.ISATOM ) return IsAtom( tree );
    else if ( functionType == FunctionType.ISPAIR ) return IsPair( tree );
    else if ( functionType == FunctionType.ISLIST ) return IsList( tree );
    else if ( functionType == FunctionType.ISNULL ) {

      int [] type = { Type.NIL };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISINTEGER ) {

      int [] type = { Type.INT };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISREAL ) {

      int [] type = { Type.INT, Type.FLOAT };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISNUMBER ) {

      int [] type = { Type.INT, Type.FLOAT };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISSTRING ) {

      int [] type = { Type.STRING };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISBOOLEAN ) {

      int [] type = { Type.T, Type.NIL };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ISSYMBOL ) {

      int [] type = { Type.SYMBOL };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.ADD ) return Calculate( tree, "+", FunctionType.ADD );
    else if ( functionType == FunctionType.SUB ) return Calculate( tree, "-", FunctionType.SUB );
    else if ( functionType == FunctionType.MUT ) return Calculate( tree, "*", FunctionType.MUT );
    else if ( functionType == FunctionType.DIV ) return Calculate( tree, "/", FunctionType.DIV );
    else if ( functionType == FunctionType.NOT ) return Not( tree );
    else if ( functionType == FunctionType.AND ) return And( tree );
    else if ( functionType == FunctionType.OR ) return Or( tree );
    else if ( functionType == FunctionType.BIGGER ) return Compare( tree, ">", FunctionType.BIGGER );
    else if ( functionType == FunctionType.BEQ ) return Compare( tree, ">=", FunctionType.BEQ );
    else if ( functionType == FunctionType.SMALLER ) return Compare( tree, "<", FunctionType.SMALLER );
    else if ( functionType == FunctionType.SEQ ) return Compare( tree, "<=", FunctionType.SEQ );
    else if ( functionType == FunctionType.EQUAL ) return Compare( tree, "=", FunctionType.EQUAL );
    else if ( functionType == FunctionType.STRAPPEND ) return StrAppend( tree );
    else if ( functionType == FunctionType.ISSTRBIGGER )

      return StrCompare( tree, "string>?", FunctionType.ISSTRBIGGER );

    else if ( functionType == FunctionType.ISSTRSMALLER )

      return StrCompare( tree, "string<?", FunctionType.ISSTRSMALLER );

    else if ( functionType == FunctionType.ISSTREQUAL )

      return StrCompare( tree, "string=?", FunctionType.ISSTREQUAL );

    else if ( functionType == FunctionType.ISEQV ) return IsEqv( tree );
    else if ( functionType == FunctionType.ISEQUAL ) return IsEqual( tree );
    else if ( functionType == FunctionType.BEGIN ) return Begin( tree );
    else if ( functionType == FunctionType.IF ) return If( tree );
    else if ( functionType == FunctionType.COND ) return Cond( tree );
    else if ( functionType == FunctionType.ELSE ) return Else();
    else if ( functionType == FunctionType.CLEAN ) return Clean( tree );
    else if ( functionType == FunctionType.EXIT ) return Exit( tree );
    else if ( functionType == FunctionType.LET ) return Let( tree );
    else if ( functionType == FunctionType.PRELAMBDA ) return PreLambda( tree );
    else if ( functionType == FunctionType.LAMBDA ) return Lambda( t, tree );
    else if ( functionType == FunctionType.VERBOSE ) return Verbose( tree );
    else if ( functionType == FunctionType.VERSTATUS ) return VerStatus( tree );
    else if ( functionType == FunctionType.CREATEERROR ) return CreateErrorObject( tree );
    else if ( functionType == FunctionType.ISERROROBJ ) {

      int [] type = { Type.ERROROBJ };

      return IsWhatType( tree, name, type );
    } // else if
    else if ( functionType == FunctionType.READ ) return Read( tree );
    else if ( functionType == FunctionType.WRITE ) return Write( tree );
    else if ( functionType == FunctionType.DISPLAYSTR ) return DisplayString( tree );
    else if ( functionType == FunctionType.NEWLINE ) return Newline( tree );
    else if ( functionType == FunctionType.EVAL ) return Eval( tree );
    else if ( functionType == FunctionType.SET ) return Set( tree );
    else if ( functionType == FunctionType.SYMBOLTOSTR ) return SymbolToString( tree );
    else if ( functionType == FunctionType.NUMBERTOSTR ) return NumberToString( tree );
    return null;
  } // DoInternalFunction()

  private TreeNode Cons( TreeNode pointer ) throws Throwable {

    TreeNode secondNode = pointer.GetmRightNode();

    if ( ! secondNode.IsTreeLeaf() ) {

      TreeNode thirdNode = secondNode.GetmRightNode();

      if ( ! thirdNode.IsTreeLeaf() ) {

        TreeNode rightNode = thirdNode.GetmRightNode();

        if ( rightNode.IsTreeLeaf() ) {

          secondNode = secondNode.GetmLeftNode();
          thirdNode = thirdNode.GetmLeftNode();

          TreeNode treeNode = new TreeNode();

          try {

            TreeNode left = CheckGrammer( secondNode );

            treeNode.SetmLeftNode( left );

            left.SetmTopNode( treeNode );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( secondNode, false ) );

            else throw e;
          } // catch

          try {

            TreeNode right = CheckGrammer( thirdNode );

            treeNode.SetmRightNode( right );

            right.SetmTopNode( right );

          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( thirdNode, false ) );

            else throw e;
          } // catch

          return treeNode;
        } // if
      } // if
    } // if

    throw new Exception( "ERROR (incorrect number of arguments) : cons" );
  } // Cons()

  private TreeNode List( TreeNode pointer ) throws Throwable {

    TreeNode returnNode = new TreeNode();
    TreeNode topReturnNode = returnNode;

    int count = 0;

    while ( pointer.HaveRight() ) {

      pointer = pointer.GetmRightNode();

      if ( pointer.IsTreeLeaf() && pointer.GetmToken().GetmType() == Type.NIL )

        returnNode.SetmToken( pointer.GetmToken() );

      else {

        try {

          TreeNode result = CheckGrammer( pointer.GetmLeftNode() );

          returnNode.SetmLeftNode( result );
          result.SetmTopNode( returnNode );

          returnNode.SetmRightNode( new TreeNode() );
          returnNode.GetmRightNode().SetmTopNode( returnNode );

          count ++;

          returnNode = returnNode.GetmRightNode();
        } // try
        catch ( Exception e ) {

          if ( e.getMessage().startsWith( "REA/" ) )

            if ( count == 0 )

              throw new Exception( "REA/ERROR (no return value) : " +
                                   mPrinter.PrintTree( pointer, false ) );
            else

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( pointer.GetmLeftNode(), false ) );

          else throw e;
        } // catch
      } // else
    } // while

    return topReturnNode;
  } // List()

  private TreeNode Quote( TreeNode pointer ) {

    TreeNode rightNode = pointer.GetmRightNode();

    return rightNode.GetmLeftNode();
  } // Quote()

  private void Define( TreeNode pointer ) throws Throwable {

    TreeNode secondArguments = pointer.GetmRightNode();

    // CheckGrammer( secondArguments.GetmLeftNode() );

    if ( secondArguments.IsTreeLeaf() )

      throw new Exception( "ERROR (DEFINE format) : " +
                           mPrinter.PrintTree( pointer, false ) );

    boolean functionDefine = false;
    VariableList variableList = new VariableList();

    String name = "";

    if ( AllTypeIsSymbol( secondArguments.GetmLeftNode() ) ) {

      if ( secondArguments.GetmLeftNode().IsTreeLeaf() ) {

        name = secondArguments.GetmLeftNode().GetmToken().GetmName();

        if ( IsInternalFunction( secondArguments.GetmLeftNode().GetmToken(), mInCond ) > 0 )

          throw new Exception( "ERROR (DEFINE format) : " +
                               mPrinter.PrintTree( pointer, false ) );
      } // if
      else {

        functionDefine = true;

        TreeNode funDefine = secondArguments.GetmLeftNode();
        Token t;

        if ( funDefine.GetmLeftNode().IsTreeLeaf() ) {

          t = funDefine.GetmLeftNode().GetmToken();
          name = t.GetmName();

        } // if
        else {

          // TreeNode nameEval = CheckGrammer( funDefine.GetmLeftNode() );
          TreeNode nameEval = funDefine.GetmLeftNode();

          if ( nameEval.IsTreeLeaf() && nameEval.GetmToken().GetmType() == Type.SYMBOL ) {

            t = nameEval.GetmToken();
            name = t.GetmName(); // TODO : maybe not use?

            if ( IsInternalFunction( t, mInCond ) > 0 )

              throw new Exception( "ERROR (DEFINE format) : " +
                                   mPrinter.PrintTree( pointer, false ) );

          } // if
          else throw new Exception( "ERROR (DEFINE format) : " +
                                    mPrinter.PrintTree( pointer, false ) );
        } // else

        if ( IsInternalFunction( t, mInCond ) > 0 )

          throw new Exception( "ERROR (DEFINE format) : " +
                               mPrinter.PrintTree( pointer, false ) );

        TreeNode argDefine = funDefine;

        while ( argDefine.HaveRight() ) {

          argDefine = argDefine.GetmRightNode();

          if ( argDefine.IsTreeLeaf() && argDefine.GetmToken().GetmType() == Type.NIL ) ;
          else if ( argDefine.IsTreeLeaf() )

            throw new Exception( "ERROR (non-list) : " + mPrinter.PrintTree( funDefine, false ) );

          else {

            VariableNode variableNode;

            TreeNode arg = argDefine.GetmLeftNode();

            if ( ! arg.IsTreeLeaf() )

              throw new Exception( "ERROR (DEFINE format) : " +
                                   mPrinter.PrintTree( pointer, false ) );

            else {

              variableNode = new VariableNode( arg.GetmToken().GetmName(), arg );

              variableList.AddIntoVariableList( variableNode );

            } // else
          } // else
        } // while
      } // else
    } // if
    else throw new Exception( "ERROR (DEFINE format) : " +
                              mPrinter.PrintTree( pointer, false ) );

    if ( ! functionDefine ) {

      TreeNode definePointer = secondArguments.GetmRightNode();

      if ( definePointer.IsTreeLeaf() )

        throw new Exception( "ERROR (DEFINE format) : " +
                             mPrinter.PrintTree( pointer,  false ) );

      else if ( ! definePointer.GetmRightNode().IsTreeLeaf() )

        throw new Exception( "ERROR (DEFINE format) : " +
                             mPrinter.PrintTree( pointer,  false ) );

      else if ( definePointer.GetmRightNode().GetmToken().GetmType() != Type.NIL )

        throw new Exception( "ERROR (DEFINE format) : " +
                             mPrinter.PrintTree( pointer,  false ) );

      try {

        TreeNode binding = CheckGrammer( definePointer.GetmLeftNode() );

        VariableNode variableNode = new VariableNode( name, binding );

        if ( mVariableList.IsInVariableList( name ) )

          mVariableList.EditItemInVariableList( variableNode );

        else

          mVariableList.AddIntoVariableList( variableNode );

      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "REA/ERROR (no return value) : " +
                               mPrinter.PrintTree( definePointer.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // if
    else {

      FunctionNode functionNode;
      Vector<TreeNode> instructionList = new Vector<TreeNode>();

      TreeNode instructionPointer = secondArguments.GetmRightNode();

      if ( instructionPointer.IsTreeLeaf() )

        throw new Exception( "ERROR (DEFINE format) : " +
                             mPrinter.PrintTree( pointer,  false ) );
      else {

        while ( ! instructionPointer.IsTreeLeaf() ) {

          instructionList.add( instructionPointer.GetmLeftNode() );

          if ( instructionPointer.HaveRight() )

            instructionPointer = instructionPointer.GetmRightNode();

        } // while

        functionNode = new FunctionNode( name, pointer, variableList, instructionList );

        if ( mFunctionList.IsInFunctionList( name ) )

          mFunctionList.EditItemInFunctionList( functionNode );

        else

          mFunctionList.AddIntoFunctionList( functionNode );
      } // else
    } // else

    throw new Exception( "Define exception," + name + " defined" );
  } // Define()

  private TreeNode Car( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() && second.GetmToken().GetmType() == Type.NIL ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );

        if ( result.IsTreeLeaf() )

          throw new Exception( "ERROR (car with incorrect argument type) : " +
                                mPrinter.PrintTree( result, false ) );

        return result.GetmLeftNode();
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "REA/ERROR (no return value) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : car" );
  } // Car()

  private TreeNode Cdr( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() && second.GetmToken().GetmType() == Type.NIL ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );

        if ( result.IsTreeLeaf() )

          throw new Exception( "ERROR (cdr with incorrect argument type) : " +
                               mPrinter.PrintTree( result, false ) );

        else
          return result.GetmRightNode();
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : cdr" );
  } // Cdr()

  private TreeNode IsAtom( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );

        Token t;

        if ( ! result.IsTreeLeaf() ) {

          t = new Token( "nil", Type.NIL );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( t );

          return returnNode;
        } // if
        else {

          t = result.GetmToken();
          int type = t.GetmType();

          if ( type == Type.SYMBOL || type == Type.INT || type == Type.FLOAT || type == Type.STRING ||
               type == Type.NIL || type == Type.T ) {

            t = new Token( "#t", Type.T );

            TreeNode returnNode = new TreeNode();

            returnNode.SetEval();

            returnNode.SetmToken( t );

            return returnNode;
          } // if
          else {

            t = new Token( "nil", Type.NIL );

            TreeNode returnNode = new TreeNode();

            returnNode.SetEval();

            returnNode.SetmToken( t );

            return returnNode;
          } // else
        } // else
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : atom?" );
  } // IsAtom()

  private TreeNode IsPair( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );
        Token t;

        if ( ! result.IsTreeLeaf() ) {

          t = new Token( "#t", Type.T );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( t );

          return returnNode;

        } // if
        else {

          t = new Token( "nil", Type.NIL );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( t );

          return returnNode;

        } // else
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : pair?" );
  } // IsPair()

  private TreeNode IsList( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );
        Token t;

        if ( result.IsTreeLeaf() ||
             result.GetDeep( 2 ).GetmToken().GetmType() != Type.NIL ) {

          t = new Token( "nil", Type.NIL );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( t );

          return returnNode;
        } // if
        else {

          t = new Token( "#t", Type.T );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( t );

          return returnNode;
        } // else
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : list?" );
  } // IsList()

  private TreeNode IsWhatType( TreeNode pointer, String funName, int[] typeList ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      try {

        TreeNode result = CheckGrammer( second.GetmLeftNode() );
        Token t;

        for ( int i = 0 ; i < typeList.length ; i ++ ) {

          int type = typeList[ i ];

          if ( result.IsTreeLeaf() && result.GetmToken().GetmType() == type ) {

            t = new Token( "#t", Type.T );

            TreeNode returnNode = new TreeNode();

            returnNode.SetEval();

            returnNode.SetmToken( t );

            return returnNode;
          } // if
        } // for

        t = new Token( "nil", Type.NIL );

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        returnNode.SetmToken( t );

        return returnNode;
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : " + funName );
  } // IsWhatType()

  private TreeNode Calculate( TreeNode pointer, String funName, int funType ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );

      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                                mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      int intResult = 0;
      float floatResult = 0;

      boolean floatCaluclate = false;

      if ( ! num1.IsTreeLeaf() )

        throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                             mPrinter.PrintTree( num1, false ) );

      else {

        Token t = num1.GetmToken();

        if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

          throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                               mPrinter.PrintTree( num1, false ) );

        else {

          if ( t.GetmType() == Type.INT ) intResult = Integer.parseInt( t.GetmName() );
          else if ( t.GetmType() == Type.FLOAT ) {

            floatResult = Float.parseFloat( t.GetmName() );
            floatCaluclate = true;
          } // else if
        } // else

        TreeNode third = second.GetmRightNode();

        if ( third.IsTreeLeaf() )

          throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

        else {

          TreeNode num2;

          try {

            num2 = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( third.GetmLeftNode(), false ) );

            else throw e;
          } // catch

          if ( ! num2.IsTreeLeaf() )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          t = num2.GetmToken();

          if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          else {

            if ( floatCaluclate ) {

              if ( funType == FunctionType.ADD )

                floatResult += Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.SUB )

                floatResult -= Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.MUT )

                floatResult *= Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.DIV ) {

                if ( Float.parseFloat( t.GetmName() ) == 0 )

                  throw new Exception( "ERROR (division by zero) : /" );

                else

                  floatResult /= Float.parseFloat( t.GetmName() );
              } // else if

              floatResult = Round( Float.parseFloat( FloatString( floatResult ) ) );
            } // if
            else if ( t.GetmType() == Type.INT ) {

              if ( funType == FunctionType.ADD )

                intResult += Integer.parseInt( t.GetmName() );

              else if ( funType == FunctionType.SUB )

                intResult -= Integer.parseInt( t.GetmName() );

              else if ( funType == FunctionType.MUT )

                intResult *= Integer.parseInt( t.GetmName() );

              else if ( funType == FunctionType.DIV ) {

                if ( Float.parseFloat( t.GetmName() ) == 0 )

                  throw new Exception( "ERROR (division by zero) : /" );

                else

                  intResult /= Integer.parseInt( t.GetmName() );
              } // else if
            } // else if
            else if ( t.GetmType() == Type.FLOAT ) {

              floatResult = intResult;
              floatCaluclate = true;

              if ( funType == FunctionType.ADD )

                floatResult += Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.SUB )

                floatResult -= Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.MUT )

                floatResult *= Float.parseFloat( t.GetmName() );

              else if ( funType == FunctionType.DIV ) {

                if ( Float.parseFloat( t.GetmName() ) == 0 )

                  throw new Exception( "ERROR (division by zero) : /" );

                else

                  floatResult /= Float.parseFloat( t.GetmName() );
              } // else if

              floatResult = Round( Float.parseFloat( FloatString( floatResult ) ) );
            } // else if
          } // else

          while ( third.HaveRight() ) {

            third = third.GetmRightNode();

            if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
            else {

              try {

                num2 = CheckGrammer( third.GetmLeftNode() );
              } // try
              catch ( Exception e ) {

                if ( e.getMessage().startsWith( "REA/" ) )

                  throw new Exception( "ERROR (unbound parameter) : " +
                                       mPrinter.PrintTree( third.GetmLeftNode(), false ) );

                else throw e;
              } // catch

              if ( ! num2.IsTreeLeaf() )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              t = num2.GetmToken();

              if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              else {

                if ( floatCaluclate ) {

                  if ( funType == FunctionType.ADD )

                    floatResult += Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.SUB )

                    floatResult -= Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.MUT )

                    floatResult *= Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.DIV ) {

                    if ( Float.parseFloat( t.GetmName() ) == 0 )

                      throw new Exception( "ERROR (division by zero) : /" );

                    else

                      floatResult /= Float.parseFloat( t.GetmName() );
                  } // else if

                  floatResult = Round( Float.parseFloat( FloatString( floatResult ) ) );
                } // if
                else if ( t.GetmType() == Type.INT ) {

                  if ( funType == FunctionType.ADD )

                    intResult += Integer.parseInt( t.GetmName() );

                  else if ( funType == FunctionType.SUB )

                    intResult -= Integer.parseInt( t.GetmName() );

                  else if ( funType == FunctionType.MUT )

                    intResult *= Integer.parseInt( t.GetmName() );

                  else if ( funType == FunctionType.DIV ) {

                    if ( Float.parseFloat( t.GetmName() ) == 0 )

                      throw new Exception( "ERROR (division by zero) : /" );

                    else

                      intResult /= Integer.parseInt( t.GetmName() );
                  } // else if
                } // else if
                else if ( t.GetmType() == Type.FLOAT ) {

                  floatResult = intResult;
                  floatCaluclate = true;

                  if ( funType == FunctionType.ADD )

                    floatResult += Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.SUB )

                    floatResult -= Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.MUT )

                    floatResult *= Float.parseFloat( t.GetmName() );

                  else if ( funType == FunctionType.DIV ) {

                    if ( Float.parseFloat( t.GetmName() ) == 0 )

                      throw new Exception( "ERROR (division by zero) : /" );

                    else

                      floatResult /= Float.parseFloat( t.GetmName() );
                  } // else if

                  floatResult = Round( Float.parseFloat( FloatString( floatResult ) ) );
                } // else if
              } // else
            } // else
          } // while

          Token returnToken;

          if ( ! floatCaluclate )

            returnToken = new Token( String.valueOf( intResult ), Type.INT );

          else

            returnToken = new Token( FloatString( floatResult ), Type.FLOAT );

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( returnToken );

          return returnNode;
        } // else
      } // else
    } // else
  } // Calculate()

  private TreeNode Not( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      Token t;

      if ( result.IsTreeLeaf() && result.GetmToken().GetmType() == Type.NIL ) {

        t = new Token( "#t", Type.T );

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        returnNode.SetmToken( t );

        return returnNode;
      } // if

      t = new Token( "nil", Type.NIL );

      TreeNode returnNode = new TreeNode();

      returnNode.SetEval();

      returnNode.SetmToken( t );

      return returnNode;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : not" );
  } // Not()

  private TreeNode And( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : and" );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : and" );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );

      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound condition) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch

      if ( num1.IsTreeLeaf() && num1.GetmToken().GetmType() == Type.NIL ) {

        TreeNode treeNode = new TreeNode();

        treeNode.SetEval();

        treeNode.SetmToken( new Token( "nil", Type.NIL ) );

        return treeNode;
      } // if

      TreeNode third = second.GetmRightNode();

      if ( third.IsTreeLeaf() )

        throw new Exception( "ERROR (incorrect number of arguments) : and" );

      else {

        TreeNode num2;

        try {

          num2 = CheckGrammer( third.GetmLeftNode() );
        } // try
        catch ( Exception e ) {

          if ( e.getMessage().startsWith( "REA/" ) )

            throw new Exception( "ERROR (unbound condition) : " +
                                 mPrinter.PrintTree( third.GetmLeftNode(), false ) );

          else throw e;
        } // catch

        if ( num2.IsTreeLeaf() && num2.GetmToken().GetmType() == Type.NIL ) {

          TreeNode treeNode = new TreeNode();

          treeNode.SetEval();

          treeNode.SetmToken( new Token( "nil", Type.NIL ) );

          return treeNode;
        } // if

        while ( third.HaveRight() ) {

          third = third.GetmRightNode();

          if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
          else {

            try {

              num2 = CheckGrammer( third.GetmLeftNode() );
            } // try
            catch ( Exception e ) {

              if ( e.getMessage().startsWith( "REA/" ) )

                throw new Exception( "ERROR (unbound condition) : " +
                                     mPrinter.PrintTree( third.GetmLeftNode(), false ) );

              else throw e;
            } // catch

            if ( num2.IsTreeLeaf() && num2.GetmToken().GetmType() == Type.NIL ) {

              TreeNode treeNode = new TreeNode();

              treeNode.SetEval();

              treeNode.SetmToken( new Token( "nil", Type.NIL ) );

              return treeNode;
            } // if
          } // else
        } // while

        return num2;
      } // else
    } // else
  } // And()

  private TreeNode Or( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : or" );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : or" );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound condition) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( num1.IsTreeLeaf() && num1.GetmToken().GetmType() == Type.NIL ) {

        TreeNode third = second.GetmRightNode();

        if ( third.IsTreeLeaf() )

          throw new Exception( "ERROR (incorrect number of arguments) : or" );

        else {

          TreeNode num2;

          try {

            num2 = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound condition) : " +
                                   mPrinter.PrintTree( third.GetmLeftNode(), false ) );
            else throw e;
          } // catch

          if ( num2.IsTreeLeaf() && num2.GetmToken().GetmType() == Type.NIL ) {

            while ( third.HaveRight() ) {

              third = third.GetmRightNode();

              if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
              else {

                try {

                  num2 = CheckGrammer( third.GetmLeftNode() );
                } // try
                catch ( Exception e ) {

                  if ( e.getMessage().startsWith( "REA/" ) )

                    throw new Exception( "ERROR (unbound condition) : " +
                                         mPrinter.PrintTree( third.GetmLeftNode(), false ) );
                  else throw e;
                } // catch

                if ( num2.IsTreeLeaf() && num2.GetmToken().GetmType() == Type.NIL ) ;
                else return num2;
              } // else
            } // while

            return num2;
          } // if
          else return num2;
        } // else
      } // if
      else return num1;
    } // else
  } // Or()

  private TreeNode Compare( TreeNode pointer, String funName, int funType ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch

      float floatResult = 0;
      boolean result = true;

      if ( ! num1.IsTreeLeaf() )

        throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                             mPrinter.PrintTree( num1, false ) );

      else {

        Token t = num1.GetmToken();

        if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

          throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                               mPrinter.PrintTree( num1, false ) );

        else

          floatResult = Float.parseFloat( t.GetmName() );

        TreeNode third = second.GetmRightNode();

        if ( third.IsTreeLeaf() )

          throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

        else {

          TreeNode num2;

          try {

            num2 = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( third.GetmLeftNode(), false ) );
            else throw e;
          } // catch

          if ( ! num2.IsTreeLeaf() )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          t = num2.GetmToken();

          if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          else {

            if ( funType == FunctionType.BIGGER ) {

              if ( floatResult <= Float.parseFloat( t.GetmName() ) )

                result = false;

              else floatResult = Float.parseFloat( t.GetmName() );
            } // if
            else if ( funType == FunctionType.BEQ ) {

              if ( floatResult < Float.parseFloat( t.GetmName() ) )

                result = false;

              else floatResult = Float.parseFloat( t.GetmName() );
            } // else if
            else if ( funType == FunctionType.SMALLER ) {

              if ( floatResult >= Float.parseFloat( t.GetmName() ) )

                result = false;

              else floatResult = Float.parseFloat( t.GetmName() );
            } // else if
            else if ( funType == FunctionType.SEQ ) {

              if ( floatResult > Float.parseFloat( t.GetmName() ) )

                result = false;

              else floatResult = Float.parseFloat( t.GetmName() );
            } // else if
            else if ( funType == FunctionType.EQUAL ) {

              if ( floatResult != Float.parseFloat( t.GetmName() ) )

                result = false;

              else floatResult = Float.parseFloat( t.GetmName() );
            } // else if

          } // else

          while ( third.HaveRight() ) {

            third = third.GetmRightNode();

            if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
            else {

              try {

                num2 = CheckGrammer( third.GetmLeftNode() );
              } // try
              catch ( Exception e ) {

                if ( e.getMessage().startsWith( "REA/" ) )

                  throw new Exception( "ERROR (unbound parameter) : " +
                                       mPrinter.PrintTree( third.GetmLeftNode(), false ) );
                else throw e;
              } // catch

              if ( ! num2.IsTreeLeaf() )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              t = num2.GetmToken();

              if ( t.GetmType() != Type.INT && t.GetmType() != Type.FLOAT )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              else {

                if ( funType == FunctionType.BIGGER ) {

                  if ( floatResult <= Float.parseFloat( t.GetmName() ) )

                    result = false;

                  else floatResult = Float.parseFloat( t.GetmName() );
                } // if
                else if ( funType == FunctionType.BEQ ) {

                  if ( floatResult < Float.parseFloat( t.GetmName() ) )

                    result = false;

                  else floatResult = Float.parseFloat( t.GetmName() );
                } // else if
                else if ( funType == FunctionType.SMALLER ) {

                  if ( floatResult >= Float.parseFloat( t.GetmName() ) )

                    result = false;

                  else floatResult = Float.parseFloat( t.GetmName() );
                } // else if
                else if ( funType == FunctionType.SEQ ) {

                  if ( floatResult > Float.parseFloat( t.GetmName() ) )

                    result = false;

                  else floatResult = Float.parseFloat( t.GetmName() );
                } // else if
                else if ( funType == FunctionType.EQUAL ) {

                  if ( floatResult != Float.parseFloat( t.GetmName() ) )

                    result = false;

                  else floatResult = Float.parseFloat( t.GetmName() );
                } // else if

              } // else
            } // else
          } // while

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          if ( result )

            returnNode.SetmToken( new Token( "#t", Type.T ) );

          else

            returnNode.SetmToken( new Token( "nil", Type.NIL ) );

          return returnNode;
        } // else
      } // else
    } // else

  } // Compare()

  private TreeNode StrAppend( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : string-append" );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : string-append" );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( ! num1.IsTreeLeaf() )

        throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                             mPrinter.PrintTree( num1, false ) );

      else {

        Token t = num1.GetmToken();
        String resultStr = "";

        if ( t.GetmType() != Type.STRING )

          throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                               mPrinter.PrintTree( num1, false ) );

        else

          resultStr =  t.GetmName();

        TreeNode third = second.GetmRightNode();

        if ( third.IsTreeLeaf() )

          throw new Exception( "ERROR (incorrect number of arguments) : string-append" );

        else {

          TreeNode num2;

          try {

            num2 = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( third.GetmLeftNode(), false ) );
            else throw e;
          } // catch

          if ( ! num2.IsTreeLeaf() )

            throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          t = num2.GetmToken();

          if ( t.GetmType() != Type.STRING )

            throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          else resultStr = resultStr.substring( 0, resultStr.length() - 1 ) +
                           t.GetmName().substring( 1 );

          while ( third.HaveRight() ) {

            third = third.GetmRightNode();

            if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
            else {

              try {

                num2 = CheckGrammer( third.GetmLeftNode() );
              } // try
              catch ( Exception e ) {

                if ( e.getMessage().startsWith( "REA/" ) )

                  throw new Exception( "ERROR (unbound parameter) : " +
                                       mPrinter.PrintTree( third.GetmLeftNode(), false ) );
                else throw e;
              } // catch

              if ( ! num2.IsTreeLeaf() )

                throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              t = num2.GetmToken();

              if ( t.GetmType() != Type.STRING )

                throw new Exception( "ERROR (string-append with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              else resultStr = resultStr.substring( 0, resultStr.length() - 1 ) +
                               t.GetmName().substring( 1 );

            } // else
          } // while

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          returnNode.SetmToken( new Token( resultStr, Type.STRING ) );

          return returnNode;
        } // else
      } // else
    } // else
  } // StrAppend()

  private TreeNode StrCompare( TreeNode pointer, String funName, int funType ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

    else {

      TreeNode num1;

      try {

        num1 = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      String compareStr = "";
      boolean result = true;

      if ( ! num1.IsTreeLeaf() )

        throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                             mPrinter.PrintTree( num1, false ) );

      else {

        Token t = num1.GetmToken();

        if ( t.GetmType() != Type.STRING )

          throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                               mPrinter.PrintTree( num1, false ) );

        else

          compareStr = t.GetmName();

        TreeNode third = second.GetmRightNode();

        if ( third.IsTreeLeaf() )

          throw new Exception( "ERROR (incorrect number of arguments) : " + funName );

        else {

          TreeNode num2;

          try {

            num2 = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( third.GetmLeftNode(), false ) );
            else throw e;
          } // catch

          if ( ! num2.IsTreeLeaf() )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          t = num2.GetmToken();

          if ( t.GetmType() != Type.STRING )

            throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                 mPrinter.PrintTree( num2, false ) );

          else {

            if ( funType == FunctionType.ISSTRBIGGER ) {

              if ( compareStr.compareTo( t.GetmName() ) <= 0 )

                result = false;

              else compareStr = t.GetmName();

            } // if
            else if ( funType == FunctionType.ISSTRSMALLER ) {

              if ( compareStr.compareTo( t.GetmName() ) >= 0 )

                result = false;

              else compareStr = t.GetmName();

            } // else if
            else if ( funType == FunctionType.ISSTREQUAL ) {

              if ( ! compareStr.equals( t.GetmName() ) )

                result = false;

            } // else if
          } // else

          while ( third.HaveRight() ) {

            third = third.GetmRightNode();

            if ( third.IsTreeLeaf() && third.GetmToken().GetmType() == Type.NIL ) ;
            else {

              try {

                num2 = CheckGrammer( third.GetmLeftNode() );
              } // try
              catch ( Exception e ) {

                if ( e.getMessage().startsWith( "REA/" ) )

                  throw new Exception( "ERROR (unbound parameter) : " +
                                       mPrinter.PrintTree( third.GetmLeftNode(), false ) );
                else throw e;
              } // catch

              if ( ! num2.IsTreeLeaf() )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              t = num2.GetmToken();

              if ( t.GetmType() != Type.STRING )

                throw new Exception( "ERROR (" + funName + " with incorrect argument type) : " +
                                     mPrinter.PrintTree( num2, false ) );

              else {

                if ( funType == FunctionType.ISSTRBIGGER ) {

                  if ( compareStr.compareTo( t.GetmName() ) <= 0 )

                    result = false;

                  else compareStr = t.GetmName();

                } // if
                else if ( funType == FunctionType.ISSTRSMALLER ) {

                  if ( compareStr.compareTo( t.GetmName() ) >= 0 )

                    result = false;

                  else compareStr = t.GetmName();
                } // else if
                else if ( funType == FunctionType.ISSTREQUAL ) {

                  if ( ! compareStr.equals( t.GetmName() ) )

                    result = false;

                } // else if
              } // else
            } // else
          } // while

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          if ( result )

            returnNode.SetmToken( new Token( "#t", Type.T ) );

          else

            returnNode.SetmToken( new Token( "nil", Type.NIL ) );

          return returnNode;
        } // else
      } // else
    } // else
  } // StrCompare()

  private TreeNode IsEqv( TreeNode pointer ) throws Throwable {

    TreeNode secondNode = pointer.GetmRightNode();

    if ( ! secondNode.IsTreeLeaf() ) {

      TreeNode thirdNode = secondNode.GetmRightNode();

      if ( ! thirdNode.IsTreeLeaf() ) {

        TreeNode rightNode = thirdNode.GetmRightNode();

        if ( rightNode.IsTreeLeaf() ) {

          secondNode = secondNode.GetmLeftNode();
          thirdNode = thirdNode.GetmLeftNode();

          TreeNode arg1;

          try {

            arg1 = CheckGrammer( secondNode );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( secondNode, false ) );

            else throw e;
          } // catch

          TreeNode arg2;

          try {

            arg2 = CheckGrammer( thirdNode );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( thirdNode, false ) );

            else throw e;
          } // catch


          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          if ( arg1 != arg2 ) {

            if ( arg1.IsTreeLeaf() && arg2.IsTreeLeaf() ) {

              if ( ! arg1.GetmToken().GetmName().equals( arg2.GetmToken().GetmName() ) )

                returnNode.SetmToken( new Token( "nil", Type.NIL ) );

              else if ( arg1.GetmToken().GetmType() == Type.STRING ||
                        arg2.GetmToken().GetmType() == Type.STRING )

                returnNode.SetmToken( new Token( "nil", Type.NIL ) );

              else

                returnNode.SetmToken( new Token( "#t", Type.T ) );
            } // if
            else returnNode.SetmToken( new Token( "nil", Type.NIL ) );
          } // if
          else returnNode.SetmToken( new Token( "#t", Type.T ) );

          return returnNode;
        } // if
      } // if
    } // if

    throw new Exception( "ERROR (incorrect number of arguments) : eqv?" );

  } // IsEqv()

  private TreeNode IsEqual( TreeNode pointer ) throws Throwable {

    TreeNode secondNode = pointer.GetmRightNode();

    if ( ! secondNode.IsTreeLeaf() ) {

      TreeNode thirdNode = secondNode.GetmRightNode();

      if ( ! thirdNode.IsTreeLeaf() ) {

        TreeNode rightNode = thirdNode.GetmRightNode();

        if ( rightNode.IsTreeLeaf() ) {

          secondNode = secondNode.GetmLeftNode();
          thirdNode = thirdNode.GetmLeftNode();

          TreeNode arg1;

          try {

            arg1 = CheckGrammer( secondNode );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( secondNode, false ) );

            else throw e;
          } // catch

          TreeNode arg2;

          try {

            arg2 = CheckGrammer( thirdNode );

          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (unbound parameter) : " +
                                   mPrinter.PrintTree( thirdNode, false ) );

            else throw e;
          } // catch

          TreeNode returnNode = new TreeNode();

          returnNode.SetEval();

          if ( arg1 != arg2 ) {

            if ( StructSame( arg1, arg2 ) )

              returnNode.SetmToken( new Token( "#t", Type.T ) );

            else

              returnNode.SetmToken( new Token( "nil", Type.NIL ) );
          } // if
          else returnNode.SetmToken( new Token( "#t", Type.T ) );

          return returnNode;
        } // if
      } // if
    } // if

    throw new Exception( "ERROR (incorrect number of arguments) : equal?" );
  } // IsEqual()

  private boolean StructSame( TreeNode arg1, TreeNode arg2 ) {

    int count = 0;

    if ( arg1.HaveLeft() && arg2.HaveLeft() ) {

      if ( StructSame( arg1.GetmLeftNode(), arg2.GetmLeftNode() ) ) count ++;
    } // if
    else if ( ! arg1.HaveLeft() && ! arg2.HaveLeft() ) count ++;
    else return false;

    if ( arg1.HaveRight() && arg2.HaveRight() ) {

      if ( StructSame( arg1.GetmRightNode(), arg2.GetmRightNode() ) ) count ++;
    } // if
    else if ( ! arg1.HaveRight() && ! arg2.HaveRight() ) count ++;
    else return false;

    if ( arg1.IsTreeLeaf() && arg2.IsTreeLeaf() ) {

      Token t1 = arg1.GetmToken();
      Token t2 = arg2.GetmToken();

      if ( t1.GetmType() != t2.GetmType() ) return false;
      else

        return t1.GetmName().equals( t2.GetmName() );

    } // if
    else if ( ! arg1.IsTreeLeaf() && ! arg2.IsTreeLeaf() ) count ++;
    else return false;

    return count == 3;

  } // StructSame()

  private TreeNode Begin( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    boolean error = false;

    if ( second.IsTreeLeaf() ) ;
    else {

      TreeNode result = null;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          error = true;

        else throw e;
      } // catch


      while ( second.HaveRight() ) {

        second = second.GetmRightNode();

        if ( second.IsTreeLeaf() ) ;
        else

          try {

            result = CheckGrammer( second.GetmLeftNode() );

            error = false;
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              error = true;

            else throw e;
          } // catch

      } // while

      if ( ! error )

        return result;

      else throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : begin" );
  } // Begin()

  private TreeNode If( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : if" );

    else if ( second.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (incorrect number of arguments) : if" );

    else {

      TreeNode con1;

      try {

        con1 = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound test-condition) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch

      TreeNode returnNode = new TreeNode();

      boolean result = true;

      if ( con1.IsTreeLeaf() && con1.GetmToken().GetmType() == Type.NIL )

        result = false;

      TreeNode third = second.GetmRightNode();

      if ( third.IsTreeLeaf() )

        throw new Exception( "ERROR (incorrect number of arguments) : if" );

      else {

        if ( result ) {

          try {

            returnNode = CheckGrammer( third.GetmLeftNode() );
          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "REA/ERROR (no return value) : " +
                                   mPrinter.PrintTree( pointer, false ) );

            else throw e;
          } // catch
        } // if

        third = third.GetmRightNode();

        if ( third.IsTreeLeaf() )

          if ( ! result )

            throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );

          else return returnNode;

        else {

          if ( ! result ) {

            TreeNode arg2;

            try {

              arg2 = CheckGrammer( third.GetmLeftNode() );
            } // try
            catch ( Exception e ) {

              if ( e.getMessage().startsWith( "REA/" ) )

                throw new Exception( "REA/ERROR (no return value) : " +
                                     mPrinter.PrintTree( pointer, false ) );

              else throw e;
            } // catch

            returnNode = arg2;

          } // if

          third = third.GetmRightNode();

          if ( third.IsTreeLeaf() ) return returnNode;
          else

            throw new Exception( "ERROR (incorrect number of arguments) : if" );

        } // else
      } // else
    } // else
  } // If()

  private TreeNode Cond( TreeNode pointer ) throws Throwable {

    TreeNode arg = pointer.GetmRightNode();
    TreeNode argStart = arg;

    if ( arg.IsTreeLeaf() ) ;
    else {

      while ( arg.HaveRight() ) {

        if ( arg.GetmLeftNode().IsTreeLeaf() )

          throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

        else if ( arg.GetmLeftNode().GetmRightNode().IsTreeLeaf() )

          throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

        else arg = arg.GetmRightNode();

      } // while

      arg = argStart;


      TreeNode cond = arg.GetmLeftNode();

      if ( cond.IsTreeLeaf() )

        throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

      else {

        int count = 0;

        while ( arg.HaveRight() ) {

          cond = arg.GetmLeftNode();

          if ( cond.IsTreeLeaf() )

            throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

          if ( arg.IsTreeLeaf() ) {

            if ( count == 0 )

              throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );
          } // if
          else {

            count ++;

            if ( arg.GetmRightNode().IsTreeLeaf() ) mInCond = true;

            TreeNode arg1;

            try {

              arg1 = CheckGrammer( cond.GetmLeftNode() );
            } // try
            catch ( Exception e ) {

              if ( e.getMessage().startsWith( "REA/" ) )

                throw new Exception( "ERROR (unbound test-condition) : " +
                                     mPrinter.PrintTree( cond.GetmLeftNode(), false ) );
              else throw e;
            } // catch


            if ( cond.GetmRightNode().IsTreeLeaf() )

              throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

            else if ( cond.GetDeep( 2 ).GetmToken().GetmType() != Type.NIL )

              throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );

            else {

              cond = cond.GetmRightNode();

              mInCond = false;

              if ( ! arg1.IsTreeLeaf() ||
                   ( arg1.IsTreeLeaf() && arg1.GetmToken().GetmType() != Type.NIL ) ) {

                TreeNode returnNode = null;

                boolean error = false;

                try {

                  returnNode = CheckGrammer( cond.GetmLeftNode() );

                } // try
                catch ( Exception e ) {

                  if ( e.getMessage().startsWith( "REA/" ) )

                    error = true;

                  else throw e;
                } // catch

                while ( cond.HaveRight() ) {

                  cond = cond.GetmRightNode();

                  if ( cond.IsTreeLeaf() ) ;
                  else

                    try {

                      returnNode = CheckGrammer( cond.GetmLeftNode() );

                      error = false;
                    } // try
                    catch ( Exception e ) {

                      if ( e.getMessage().startsWith( "REA/" ) )

                        error = true;

                      else throw e;
                    } // catch

                } // while

                if ( ! error )

                  return returnNode;

                else

                  throw new Exception( "REA/ERROR (no return value) : " +
                                       mPrinter.PrintTree( pointer, false ) );
              } // if
              else arg = arg.GetmRightNode();
            } // else
          } // else
        } // while

        throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );

      } // else

    } // else

    throw new Exception( "ERROR (COND format) : " + mPrinter.PrintTree( pointer, false ) );
  } // Cond()

  private TreeNode Else() {

    TreeNode treeNode = new TreeNode();

    treeNode.SetEval();

    treeNode.SetmToken( new Token( "else", Type.FUNCTION ) );

    return treeNode;

  } // Else()

  private TreeNode Clean( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( ! second.IsTreeLeaf() ) ;
    else {

      mVariableList.ClearVariableList();
      mFunctionList.ClearFunctionList();

      if ( mVerbose )

        System.out.println( "environment cleaned" );

      throw new Exception( "Clean-Environment exception" );

    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : clean-environment" );

  } // Clean()

  private TreeNode Exit( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( ! second.IsTreeLeaf() ) ;
    else {

      throw new Exception( "Exit exception" );

    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : exit" );

  } // Exit()

  private TreeNode RunFunction( FunctionNode functionNode, TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();
    Vector<Integer> variableDefinePos = new Vector<Integer>();
    VariableList variableList = functionNode.GetmVariableList();
    VariableList tempVariablelist = new VariableList();

    int funVariableSize = variableList.GetVariableListSize();
    int variableCount = 0;

    TreeNode oldSecondNode = second;

    while ( ! second.IsTreeLeaf() ) {

      variableCount ++ ;

      if ( variableCount > funVariableSize )

        throw new Exception( "ERROR (incorrect number of arguments) : " + functionNode.GetmName() );

      second = second.GetmRightNode();
    } // while

    if ( variableCount != funVariableSize )

      throw new Exception( "ERROR (incorrect number of arguments) : " + functionNode.GetmName() );

    second = oldSecondNode;

    variableCount = 0;

    while ( ! second.IsTreeLeaf() ) {

      variableCount ++;

      try {

        TreeNode evalResult = CheckGrammer( second.GetmLeftNode() );

        String name = variableList.FindInVariableList( variableCount - 1 ).GetName();

        VariableNode variableNode = new VariableNode( name, evalResult );

        tempVariablelist.AddIntoVariableList( variableNode );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      second = second.GetmRightNode();
    } // while

    variableDefinePos = mVariableList.ConnectVariableList( tempVariablelist );

    Vector<TreeNode> instructionsList = functionNode.GetmInstructionList();

    TreeNode returnNode = new TreeNode();

    // int nowLevel = mLevel;

    for ( int i = 0 ; i < instructionsList.size() ; i ++ ) {

      try {

        // mLevel = 0;

        returnNode = CheckGrammer( instructionsList.elementAt( i ) );

      } // try
      catch ( Exception e ) {

        returnNode = null;

        if ( e.getMessage().startsWith( "REA/" ) && i == instructionsList.size() - 1 ) {

          mVariableList.RemoveVariableUseList( variableDefinePos );

          throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );
        } // if
        else if ( e.getMessage().startsWith( "REA/" ) ) ;
        else {

          mVariableList.RemoveVariableUseList( variableDefinePos );

          throw e;
        } // else
      } // catch
    } // for

    // mLevel = nowLevel;

    mVariableList.RemoveVariableUseList( variableDefinePos );

    if ( returnNode == null )

      throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );

    return returnNode;
  } // RunFunction()

  private TreeNode Let( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().IsTreeLeaf() ) ;
    else {

      TreeNode defineArg = second.GetmLeftNode();

      if ( defineArg.IsTreeLeaf() && defineArg.GetmToken().GetmType() != Type.NIL )

        throw new Exception( "ERROR (LET format) : " +
                             mPrinter.PrintTree( pointer, false ) );

      else if ( ! defineArg.IsTreeLeaf() &&
                ( ! defineArg.GetDeep( 2 ).IsTreeLeaf() ||
                  defineArg.GetDeep( 2 ).GetmToken().GetmType() != Type.NIL ) )

        throw new Exception( "ERROR (non-list) : " + mPrinter.PrintTree( defineArg, false ) );

      else {

        VariableList tempVariableList = new VariableList();
        Vector<Integer> variablePosition = new Vector<Integer>();
        Vector<String> nameList = new Vector<String>();

        TreeNode topDefineArg = defineArg;

        while ( ! defineArg.IsTreeLeaf() ) {

          TreeNode arg = defineArg.GetmLeftNode();

          if ( arg.IsTreeLeaf() )

            throw new Exception( "ERROR (LET format) : " +
                                 mPrinter.PrintTree( pointer, false ) );

          else if ( ! arg.GetDeep( 2 ).IsTreeLeaf() ||
                    ( arg.GetDeep( 2 ).IsTreeLeaf() &&
                      arg.GetDeep( 2 ).GetmToken().GetmType() != Type.NIL ) )

            throw new Exception( "ERROR (non-list) : " + mPrinter.PrintTree( arg, false ) );

          else {

            TreeNode nameEval = arg.GetmLeftNode();

            if ( ! nameEval.IsTreeLeaf() ||
                 ( nameEval.IsTreeLeaf() && nameEval.GetmToken().GetmType() != Type.SYMBOL ) )

              throw new Exception( "ERROR (LET format) : " +
                                   mPrinter.PrintTree( pointer, false ) );

            arg = arg.GetmRightNode();

            if ( arg.IsTreeLeaf() || ! arg.GetmRightNode().IsTreeLeaf() )

              throw new Exception( "ERROR (LET format) : " +
                                   mPrinter.PrintTree( pointer, false ) );

            else if ( arg.GetmLeftNode().IsTreeLeaf() ) {

              if ( nameList.contains( arg.GetmLeftNode().GetmToken().GetmName() ) )

                throw new Exception( "ERROR (LET format) : " +
                                     mPrinter.PrintTree( pointer, false ) );
            } // else if

            nameList.add( nameEval.GetmToken().GetmName() );
          } // else

          defineArg = defineArg.GetmRightNode();

        } // while

        defineArg = topDefineArg;

        while ( ! defineArg.IsTreeLeaf() ) {

          TreeNode arg = defineArg.GetmLeftNode();
          TreeNode nameEval = arg.GetmLeftNode();

          String name;

          name = nameEval.GetmToken().GetmName();

          if ( IsInternalFunction( nameEval.GetmToken(), mInCond ) > 0 )

            throw new Exception( "ERROR (LET format) : " +
                                 mPrinter.PrintTree( pointer, false ) );

          arg = arg.GetmRightNode();

          try {

            TreeNode binding = CheckGrammer( arg.GetmLeftNode() );

            VariableNode variableNode = new VariableNode( name, binding );

            tempVariableList.AddIntoVariableList( variableNode );

          } // try
          catch ( Exception e ) {

            if ( e.getMessage().startsWith( "REA/" ) )

              throw new Exception( "ERROR (no return value) : " +
                                   mPrinter.PrintTree( arg.GetmLeftNode(), false ) );

            else throw e;
          } // catch

          defineArg = defineArg.GetmRightNode();

        } // while

        variablePosition = mVariableList.ConnectVariableList( tempVariableList, false );

        TreeNode returnNode = new TreeNode();
        boolean returnError = false;

        while ( second.HaveRight() ) {

          second = second.GetmRightNode();

          if ( second.IsTreeLeaf() ) ;
          else {

            try {

              returnNode = CheckGrammer( second.GetmLeftNode() );

              returnError = false;
            } // try
            catch ( Exception e ) {

              returnNode = null;

              if ( e.getMessage().startsWith( "REA/" ) )

                returnError = true;

              else {

                mVariableList.RemoveVariableUseList( variablePosition, false );

                throw e;
              } // else
            } // catch
          } // else
        } // while

        mVariableList.RemoveVariableUseList( variablePosition, false );

        if ( returnError )

          throw new Exception( "REA/ERROR (no return value) : " +
                               mPrinter.PrintTree( pointer, false ) );

        return returnNode;
      } // else
    } // else

    throw new Exception( "ERROR (LET format) : " +
                         mPrinter.PrintTree( pointer, false ) );
  } // Let()

  private TreeNode PreLambda( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else {

      VariableList variableList = new VariableList();
      Vector<TreeNode> instructionList = new Vector<TreeNode>();

      if ( second.GetmLeftNode().IsTreeLeaf() && second.GetmLeftNode().GetmToken().GetmType() != Type.NIL )

        throw new Exception( "ERROR (LAMBDA format) : " +
                             mPrinter.PrintTree( pointer, false ) );

      else if ( second.GetmLeftNode().IsTreeLeaf() ) ;
      else {

        String name;

        TreeNode varNode = second.GetmLeftNode();

        if ( varNode.GetmLeftNode().IsTreeLeaf() &&
             varNode.GetmLeftNode().GetmToken().GetmType() != Type.SYMBOL )

          throw new Exception( "ERROR (LAMBDA format) : " +
                               mPrinter.PrintTree( pointer, false ) );

        else {

          TreeNode nameEval = varNode.GetmLeftNode();

          if ( ! nameEval.IsTreeLeaf() ||
               ( nameEval.IsTreeLeaf() && nameEval.GetmToken().GetmType() != Type.SYMBOL ) )

            throw new Exception( "ERROR (LAMBDA format) : " +
                                 mPrinter.PrintTree( pointer, false ) );

          else if ( IsInternalFunction( nameEval.GetmToken(), mInCond ) > 0 )

            throw new Exception( "ERROR (LAMBDA format) : " +
                                 mPrinter.PrintTree( pointer, false ) );
          else

            name = nameEval.GetmToken().GetmName();

          VariableNode variableNode = new VariableNode( name, nameEval );

          variableList.AddIntoVariableList( variableNode );

          while ( varNode.HaveRight() ) {

            varNode = varNode.GetmRightNode();

            if ( varNode.IsTreeLeaf() ) ;
            else {

              nameEval = varNode.GetmLeftNode();

              if ( ! nameEval.IsTreeLeaf() ||
                   ( nameEval.IsTreeLeaf() && nameEval.GetmToken().GetmType() != Type.SYMBOL ) )

                throw new Exception( "ERROR (LAMBDA format) : " +
                                     mPrinter.PrintTree( pointer, false ) );
              else

                name = nameEval.GetmToken().GetmName();

              variableNode = new VariableNode( name, nameEval );

              variableList.AddIntoVariableList( variableNode );

            } // else
          } // while
        } // else
      } // else

      if ( second.GetmRightNode().IsTreeLeaf() )

        throw new Exception( "ERROR (LAMBDA format) : " + mPrinter.PrintTree( pointer, false ) );

      else {

        TreeNode instruction = second;

        while ( instruction.HaveRight() ) {

          instruction = instruction.GetmRightNode();

          if ( instruction.IsTreeLeaf() ) ;
          else

            instructionList.add( instruction.GetmLeftNode() );
        } // while

        FunctionNode lambdaFunction = new FunctionNode( "lambda", pointer, variableList, instructionList );

        int pos = mLambdaFunList.AddIntoFunctionList( lambdaFunction );

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        Token t = new Token( "lambda", Type.LAMBDA );

        t.SetmPosition( pos );

        returnNode.SetmToken( t );

        return returnNode;
      } // else
    } // else

    throw new Exception( "ERROR (LAMBDA format) : " + mPrinter.PrintTree( pointer, false ) );
  } // PreLambda()

  private TreeNode Lambda( Token fun, TreeNode pointer ) throws Throwable {

    FunctionNode funNode = mLambdaFunList.FindInFunctionList( fun.GetmPosition() );

    TreeNode second = pointer.GetmRightNode();
    Vector<Integer> variableDefinePos = new Vector<Integer>();
    VariableList variableList = funNode.GetmVariableList();
    VariableList tempVariablelist = new VariableList();

    int funVariableSize = variableList.GetVariableListSize();
    int variableCount = 0;

    TreeNode oldSecondNode = second;

    while ( ! second.IsTreeLeaf() ) {

      variableCount ++ ;

      if ( variableCount > funVariableSize )

        throw new Exception( "ERROR (incorrect number of arguments) : lambda" );

      second = second.GetmRightNode();
    } // while

    if ( variableCount != funVariableSize )

      throw new Exception( "ERROR (incorrect number of arguments) : lambda" );

    second = oldSecondNode;

    variableCount = 0;

    while ( ! second.IsTreeLeaf() ) {

      variableCount ++;

      try {

        TreeNode evalResult = CheckGrammer( second.GetmLeftNode() );

        String name = variableList.FindInVariableList( variableCount - 1 ).GetName();

        VariableNode variableNode = new VariableNode( name, evalResult );

        tempVariablelist.AddIntoVariableList( variableNode );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      second = second.GetmRightNode();
    } // while

    variableDefinePos = mVariableList.ConnectVariableList( tempVariablelist );

    Vector<TreeNode> instructionsList = funNode.GetmInstructionList();

    TreeNode returnNode = new TreeNode();


    for ( int i = 0 ; i < instructionsList.size() ; i ++ ) {

      try {

        returnNode = CheckGrammer( instructionsList.elementAt( i ) );

      } // try
      catch ( Exception e ) {

        returnNode = null;

        if ( e.getMessage().startsWith( "REA/" ) && i == instructionsList.size() - 1 ) {

          mVariableList.RemoveVariableUseList( variableDefinePos );

          throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );
        } // if
        else if ( e.getMessage().startsWith( "REA/" ) ) ;
        else {

          mVariableList.RemoveVariableUseList( variableDefinePos );

          throw e;
        } // else
      } // catch
    } // for

    mVariableList.RemoveVariableUseList( variableDefinePos );

    if ( returnNode == null )

      throw new Exception( "REA/ERROR (no return value) : " + mPrinter.PrintTree( pointer, false ) );

    return returnNode;
  } // Lambda()

  private TreeNode Verbose( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );

        else throw e;
      } // catch

      Token t;

      if ( result.IsTreeLeaf() && result.GetmToken().GetmType() == Type.NIL ) {

        mVerbose = false;

        t = new Token( "nil", Type.NIL );

        TreeNode returnNode = new TreeNode();

        returnNode.SetEval();

        returnNode.SetmToken( t );

        return returnNode;
      } // if

      mVerbose = true;

      t = new Token( "#t", Type.T );

      TreeNode returnNode = new TreeNode();

      returnNode.SetEval();

      returnNode.SetmToken( t );

      return returnNode;

    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : verbose" );

  } // Verbose()

  private TreeNode VerStatus( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( ! second.IsTreeLeaf() ) ;
    else {

      TreeNode returnNode = new TreeNode();

      returnNode.SetEval();

      if ( mVerbose )

        returnNode.SetmToken( new Token( "#t", Type.T ) );

      else

        returnNode.SetmToken( new Token( "nil", Type.NIL ) );

      return returnNode;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : verbose?" );

  } // VerStatus()

  private TreeNode CreateErrorObject( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( ! result.IsTreeLeaf() ||
           ( result.IsTreeLeaf() && result.GetmToken().GetmType() != Type.STRING ) )

        throw new Exception( "ERROR (create-error-object with incorrect argument type) : " +
                             mPrinter.PrintTree( result, false ) );

      else {

        result.GetmToken().SetmType( Type.ERROROBJ );

        return result;

      } // else
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : create-error-object" );
  } // CreateErrorObject()

  private TreeNode Read( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( ! second.IsTreeLeaf() ) ;
    else {

      try {

        mStatementGet.GetStatement( true );

        TreeNode result = mStatementGet.GetTree();

        // mPrinter.PrintTree( mStatementGet.GetTree(), true );

        return result;
      } // try
      catch ( Exception e ) {

        TreeNode errorReturnNode = new TreeNode();

        Token t;

        if ( e.getMessage().contains( "END-OF-FILE" ) )

          t = new Token( "\"END-OF-FILE encountered when there should be more input\"",
                         Type.ERROROBJ );
        else

          t = new Token( "\"" + e.getMessage() + "\"", Type.ERROROBJ );

        errorReturnNode.SetmToken( t );

        errorReturnNode.SetEval();

        mStatementGet.SkipLine();

        return errorReturnNode;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : read" );
  } // Read()

  private TreeNode Write( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      String print = mPrinter.PrintTree( result, false );

      System.out.print( print.substring( 0, print.length() - 1 ) );

      return result;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : write" );

  } // Write()

  private TreeNode DisplayString( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( ! result.IsTreeLeaf() ||
           ( result.IsTreeLeaf() && result.GetmToken().GetmType() != Type.STRING &&
             result.GetmToken().GetmType() != Type.ERROROBJ ) )

        throw new Exception( "ERROR (write with incorrect argument type) : " +
                             mPrinter.PrintTree( result, false ) );

      String print = mPrinter.PrintTree( result, false );

      System.out.print( print.substring( 1, print.length() - 2 ) );

      return result;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : write" );

  } // DisplayString()

  private TreeNode Newline( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( ! second.IsTreeLeaf() ) ;
    else {

      System.out.println();

      TreeNode returnNode = new TreeNode();

      returnNode.SetmToken( new Token( "nil", Type.NIL ) );

      returnNode.SetEval();

      return returnNode;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : newline" );

  } // Newline()

  private TreeNode Eval( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (no return value) : " +
                               mPrinter.PrintTree( pointer, false ) );
        else throw e;
      } // catch

      int level = mLevel;

      try {

        mLevel = 0;

        TreeNode evalResult = CheckGrammer( result );

        mLevel = level;

        return evalResult;
      } // try
      catch ( Exception e ) {

        mLevel = level;

        if ( e.getMessage().contains( "Define exception" ) ) {

          if ( mVerbose )

            System.out.println( e.getMessage().substring( e.getMessage().indexOf( "," ) + 1 ) );

          return null;
        } // if
        else if ( e.getMessage().equals( "Clean-Environment exception" ) )

          mInstructionList.clear();

        else if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (no return value) : " +
                               mPrinter.PrintTree( pointer, false ) );

        else throw e;
      } // catch
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : eval" );
  } // Eval()

  private TreeNode Set( TreeNode pointer ) throws Throwable {

    TreeNode secondArguments = pointer.GetmRightNode();

    if ( secondArguments.IsTreeLeaf() )

      throw new Exception( "ERROR (SET! format) : " +
                           mPrinter.PrintTree( pointer, false ) );

    String name = "";

    if ( AllTypeIsSymbol( secondArguments.GetmLeftNode() ) ) {

      if ( secondArguments.GetmLeftNode().IsTreeLeaf() ) {

        name = secondArguments.GetmLeftNode().GetmToken().GetmName();

        if ( IsInternalFunction( secondArguments.GetmLeftNode().GetmToken(), mInCond ) > 0 )

          throw new Exception( "ERROR (SET! format) : " +
                               mPrinter.PrintTree( pointer, false ) );
      } // if
      else throw new Exception( "ERROR (SET! format) : " +
                                mPrinter.PrintTree( pointer, false ) );
    } // if
    else throw new Exception( "ERROR (SET! format) : " +
                              mPrinter.PrintTree( pointer, false ) );


    TreeNode definePointer = secondArguments.GetmRightNode();

    if ( definePointer.IsTreeLeaf() )

      throw new Exception( "ERROR (SET! format) : " +
                           mPrinter.PrintTree( pointer,  false ) );

    else if ( ! definePointer.GetmRightNode().IsTreeLeaf() )

      throw new Exception( "ERROR (SET! format) : " +
                           mPrinter.PrintTree( pointer,  false ) );

    else if ( definePointer.GetmRightNode().GetmToken().GetmType() != Type.NIL )

      throw new Exception( "ERROR (SET! format) : " +
                           mPrinter.PrintTree( pointer,  false ) );

    try {

      TreeNode binding = CheckGrammer( definePointer.GetmLeftNode() );

      VariableNode variableNode = new VariableNode( name, binding );

      if ( mVariableList.IsInVariableList( name ) )

        mVariableList.EditItemInVariableList( variableNode );

      else

        mVariableList.AddIntoVariableList( variableNode );

      return binding;
    } // try
    catch ( Exception e ) {

      if ( e.getMessage().startsWith( "REA/" ) )

        throw new Exception( "REA/ERROR (no return value) : " +
                             mPrinter.PrintTree( definePointer.GetmLeftNode(), false ) );

      else throw e;
    } // catch
  } // Set()

  private TreeNode SymbolToString( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( ! result.IsTreeLeaf() ||
           ( result.IsTreeLeaf() && result.GetmToken().GetmType() != Type.SYMBOL ) )

        throw new Exception( "ERROR (symbol->string with incorrect argument type) : " +
                             mPrinter.PrintTree( result, false ) );

      String print = mPrinter.PrintTree( result, false );

      TreeNode returnNode = new TreeNode();

      returnNode.SetmToken( new Token( "\"" + print.substring( 0, print.length() - 1 ) + "\"",
                                       Type.STRING ) );

      returnNode.SetEval();

      return returnNode;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : symbol->string" );

  } // SymbolToString()

  private TreeNode NumberToString( TreeNode pointer ) throws Throwable {

    TreeNode second = pointer.GetmRightNode();

    if ( second.IsTreeLeaf() ) ;
    else if ( ! second.GetmRightNode().IsTreeLeaf() ) ;
    else if ( second.GetmRightNode().GetmToken().GetmType() != Type.NIL ) ;
    else {

      TreeNode result;

      try {

        result = CheckGrammer( second.GetmLeftNode() );
      } // try
      catch ( Exception e ) {

        if ( e.getMessage().startsWith( "REA/" ) )

          throw new Exception( "ERROR (unbound parameter) : " +
                               mPrinter.PrintTree( second.GetmLeftNode(), false ) );
        else throw e;
      } // catch

      if ( ! result.IsTreeLeaf() ||
           ( result.IsTreeLeaf() && result.GetmToken().GetmType() != Type.INT &&
             result.GetmToken().GetmType() != Type.FLOAT ) )

        throw new Exception( "ERROR (number->string with incorrect argument type) : " +
                             mPrinter.PrintTree( result, false ) );

      String print = mPrinter.PrintTree( result, false );

      TreeNode returnNode = new TreeNode();

      returnNode.SetmToken( new Token( "\"" + print.substring( 0, print.length() - 1 ) + "\"",
                                       Type.STRING ) );

      returnNode.SetEval();

      return returnNode;
    } // else

    throw new Exception( "ERROR (incorrect number of arguments) : number->string" );

  } // NumberToString()

  private float Round( float input ) {

    input = ( float ) ( input * 1000.0 );

    if ( input % 1 > 0.5 )

      input = ( float ) ( ( ( float ) ( ( int ) input ) + 1 ) / 1000.0 );

    else input = ( float ) ( ( float ) ( ( int ) input ) / 1000.0 );

    String inputString = String.valueOf( input );

    String underPoint = inputString.substring( inputString.indexOf( "." ) + 1 );

    if ( underPoint.equals( "" ) )

      inputString += "000";

    else if ( underPoint.length() == 1 )

      inputString += "00";

    else if ( underPoint.length() == 2 )

      inputString += "0";

    return Float.parseFloat( inputString );
  } // Round()

  private String FloatString( float input ) {

    String inputString = String.valueOf( input );

    String upPoint = inputString.substring( 0, inputString.indexOf( "." ) );
    String underPoint = inputString.substring( inputString.indexOf( "." ) + 1 );

    if ( upPoint.equals( "" ) )

      inputString = "0" + inputString;

    else if ( upPoint.equals( "-" ) )

      inputString = "-0.";

    if ( underPoint.equals( "" ) )

      inputString += "000";

    else if ( underPoint.length() == 1 )

      inputString += "00";

    else if ( underPoint.length() == 2 )

      inputString += "0";

    return inputString;
  } // FloatString()

  private boolean AllTypeIsSymbol( TreeNode pointer ) {

    int count = 0;

    if ( pointer.HaveLeft() ) {

      if ( AllTypeIsSymbol( pointer.GetmLeftNode() ) ) count ++;
    } // if
    else count ++;

    if ( pointer.HaveRight() ) {

      if ( AllTypeIsSymbol( pointer.GetmRightNode() ) ) count ++;
      else if ( pointer.GetmRightNode().IsTreeLeaf() &&
                pointer.GetmRightNode().GetmToken().GetmType() == Type.NIL ) count ++;
    } // if
    else count ++;

    if ( pointer.IsTreeLeaf() ) {

      if ( pointer.GetmToken().GetmType() == Type.SYMBOL ) count ++;

    } // if
    else count ++;

    return count == 3;

  } // AllTypeIsSymbol()

} // class Processer

class WorkSpace {

  private int mTestNumber;

  GetToken mGetToken = null;
  StatementGet mStatementGet = null;
  Vector<Token> mTokenList;
  Printer mPrinter;
  Processer mProcesser;

  public void Project() throws Throwable {

    ICEInputStream  inputStream = new ICEInputStream();

    System.out.println( "Welcome to OurScheme!" );
    System.out.println();

    mGetToken = new GetToken( inputStream );
    mStatementGet = new StatementGet( inputStream );
    mProcesser = new Processer( mStatementGet );
    mPrinter = new Printer( mProcesser );

    mTestNumber = inputStream.ReadInt();
    inputStream.ReadChar();

    mTokenList = new Vector<Token>();

    boolean run = true;

    while ( run && ! mGetToken.IsAtEOF() ) {

      System.out.print( "> " );

      mTokenList = mStatementGet.GetStatement();

      if ( ! mTokenList.isEmpty() ) {

        // mPrinter.PrintTree( mStatementGet.GetTree(), true );

        try {

          mProcesser.AddInstruction( mStatementGet.GetTree() );

          System.out.println();
        } // try
        catch ( Exception e ) {

          if ( e.getMessage().equals( "Exit exception" ) ) {

            run = false;

            System.out.println();
          } // if
          else

            System.out.println( e.getMessage() );

        } // catch()
      } // if

      /*
      for ( int i = 0 ; i <  mTokenList.size() ; i ++ ) {

        Token t =  mTokenList.elementAt( i ) ;

        System.out.println( t.GetmName() + "\t L:" + t.GetmLine() + "\t C:"
                            + t.GetmPosition() + "\t T:" + t.GetmType() );
      } // for
      */

    } // while

    if ( run && ! mGetToken.GetEofError() && ! mStatementGet.GetEofError() )

      System.out.println( "> ERROR (no more input) : END-OF-FILE encountered" );

    System.out.println( "Thanks for using OurScheme!" );

  } // Project()

} // class WorkSpace


class Main {

  public static void main( String[] args ) throws Throwable {

    WorkSpace workSpace = new WorkSpace();

    workSpace.Project();

  } // main()

} // class Main
