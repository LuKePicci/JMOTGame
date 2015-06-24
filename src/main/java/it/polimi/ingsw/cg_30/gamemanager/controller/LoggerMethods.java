package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.EmptyStackException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

/**
 * The Class LoggerMethods.
 */
public class LoggerMethods {

    /** The logger. */
    private static final Logger logger = Logger.getLogger("");

    /**
     * Prevents the creation of an instance of LoggerMethods.
     */
    private LoggerMethods() {
    }

    /**
     * Empty stack exception logger.
     *
     * @param e
     *            the exception
     * @param str
     *            the comment to that exception
     */
    public static void emptyStackException(EmptyStackException e, String str) {
        logger.info("EmptyStackException: " + e.getMessage() + str);
    }

    /**
     * Disconnected exception logger.
     *
     * @param e
     *            the exception
     * @param str
     *            the comment to that exception
     */
    public static void disconnectedException(DisconnectedException e, String str) {
        logger.info("DisconnectedException: " + e.getMessage() + str);
    }

    /**
     * Reflective operation exception logger.
     *
     * @param e
     *            the exception
     * @param str
     *            the comment to that exception
     */
    public static void reflectiveOperationException(
            ReflectiveOperationException e, String str) {
        logger.info("ReflectiveOperationException: " + e.getMessage() + str);
    }

    /**
     * Not an hatch exception logger.
     *
     * @param e
     *            the exception
     * @param str
     *            the comment to that exception
     */
    public static void notAnHatchException(NotAnHatchException e, String str) {
        logger.info("NotAnHatchException: " + e.getMessage() + str);
    }

    /**
     * Warning logger.
     *
     * @param string
     *            the message
     */
    public static void warning(String string) {
        logger.warning(string);
    }

    /**
     * JAXBE exception logger.
     *
     * @param e
     *            the exception
     */
    public static void jAXBException(JAXBException e) {
        logger.info(e.getMessage());
    }

    /**
     * File not found exception logger.
     *
     * @param e
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void fileNotFoundException(FileNotFoundException e,
            String string) {
        logger.info("FileNotFoundException: " + e.getMessage() + string);
    }

    /**
     * IO exception logger.
     *
     * @param e
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void iOException(IOException e, String string) {
        logger.info("IOException: " + e.getMessage() + string);
    }

    /**
     * Exception logger.
     *
     * @param ex
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void exception(Exception ex, String string) {
        logger.info("Exception: " + ex.getMessage() + string);
    }

    /**
     * Remote exception logger.
     *
     * @param e
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void remoteException(RemoteException e, String string) {
        logger.info("RemoteException: " + e.getMessage() + string);
    }

    /**
     * Invalid parameter exception logger.
     *
     * @param ex
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void invalidParameterException(InvalidParameterException ex,
            String string) {
        logger.info("InvalidParameterException: " + ex.getMessage() + string);
    }

    /**
     * Interrupted exception.
     *
     * @param e
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void interruptedException(InterruptedException e,
            String string) {
        logger.info("InterruptedException: " + e.getMessage() + string);
    }

    /**
     * Illegal argument exception logger.
     *
     * @param ex
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void illegalArgumentException(IllegalArgumentException ex,
            String string) {
        logger.info("IllegalArgumentException: " + ex.getMessage() + string);
    }

    /**
     * Unsupported operation exception logger.
     *
     * @param ex
     *            the exception
     * @param string
     *            the comment to that exception
     */
    public static void unsupportedOperationException(
            UnsupportedOperationException ex, String string) {
        logger.info("UnsupportedOperationException: " + ex.getMessage()
                + string);
    }

}
