import os , sys

def create():
    from sqlalchemy import Table
    import model
    for (name, table) in vars(model).iteritems():
        if isinstance(table,Table):
            table.create()

if __name__=="__main__":
    if 'create' in sys.argv:
        create()
