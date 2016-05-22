package com.frusby.sumptusmagnus.core;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by alexisjouhault on 5/21/16.
 */
public class DatabaseManager {

    private final static DatabaseManager databaseManager = new DatabaseManager();
    private SugarRecord record = new SugarRecord();

    private DatabaseManager() {

    }

    public static DatabaseManager getInstance() {
        return databaseManager;
    }

    public void addElem(SugarRecord elem) {
        elem.save();
    }

    public SugarRecord findElemById(Class<SugarRecord> elemclass, int id) {
        SugarRecord elem = record.findById(elemclass, id);
        return elem;
    }

    public List<SugarRecord> findAll(Class<SugarRecord> elemclass) {
        List<SugarRecord> elems = record.listAll(elemclass);
        return elems;
    }

}
