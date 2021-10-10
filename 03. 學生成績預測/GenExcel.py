import pandas as pd
import xlsxwriter

def genExcel( test_data, predictAns ):

    test_data = pd.DataFrame(test_data)
    predictAns = pd.DataFrame(predictAns)

    writer = pd.ExcelWriter('Predict_Result.xlsx', engine='xlsxwriter')

    test_data = test_data.style.set_properties(**{'text-align': 'center'})
    predictAns = predictAns.style.set_properties(**{'text-align': 'center'})

    test_data.to_excel(writer, sheet_name = 'predict', startrow = 1)
    predictAns.to_excel(writer, sheet_name='predict', startrow = 5)

    worksheet = writer.sheets['predict']

    worksheet.set_column('A:A', 16)
    worksheet.set_column('B:P', 12)

    for i in range(0, 20) :

        worksheet.set_row(i, 20)

    # worksheet.set_column('G:G', 17)
    worksheet.write(0, 0, "預測資料")
    worksheet.write(4, 0, "預測結果")

    writer.save()