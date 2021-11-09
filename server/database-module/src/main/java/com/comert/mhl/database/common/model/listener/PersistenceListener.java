package com.comert.mhl.database.common.model.listener;

import com.comert.mhl.database.common.model.entity.GenericEntity;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * sadece remove olursa ( mail recordlar hariç) trigger gibi başka tabloya yazacağız.
 */
public class PersistenceListener {

    private static Logger logger = Logger.getLogger(GenericEntity.class.getName());
    private static final String FILE_PATTERN = GenericEntity.class.getName() + "-%g.log.txt";
    private static final int FILE_SIZE = 1000000;
    private static final int FILE_COUNT = 1000;

    static {
        try {
            FileHandler fileHandler = new FileHandler(FILE_PATTERN, FILE_SIZE, FILE_COUNT);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException | SecurityException e) {
            Logger.getGlobal().log(Level.SEVERE, "Logger file handler creation error ", e);
        }
    }

    private static void info(String template, Object... arguments) {
        logger.log(Level.INFO, "[MHL] " + template, arguments);
    }

    private static void warning(String template, Object... arguments) {
        logger.log(Level.WARNING, "[MHL] " + template, arguments);
    }


    @PrePersist
    public void onPrePersist(GenericEntity baseEntity) {
    }

    @PostPersist
    public void onPostPersist(GenericEntity baseEntity) {
    }

    @PreUpdate
    public void onPreUpdate(GenericEntity baseEntity) {
    }

    @PostUpdate
    public void onPostUpdate(GenericEntity baseEntity) {
    }

    @PreRemove
    public void onPreRemove(GenericEntity baseEntity) {
    }

    @PostRemove
    public void onPostRemove(GenericEntity baseEntity) {
    }

}
