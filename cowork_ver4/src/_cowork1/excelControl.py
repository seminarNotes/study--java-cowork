import win32com.client as win32

PATH = r"C:/ITStudy/04_java/cowork/src/_cowork1/"
FILE = r"test1.xlsx"

excel = win32.Dispatch("Excel.Application")
excel.Visiable = True
wb = excel.Workbooks.Add()
ws = wb.WorkSheets("Sheet1")
ws.Cells(1, 1).Value




