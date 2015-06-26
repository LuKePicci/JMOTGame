package it.polimi.ingsw.cg_30.exchange.messaging;

import it.polimi.ingsw.cg_30.gamemanager.controller.NotAnHatchException;
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
    private static final Logger LOGGER = Logger.getLogger("");

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
        LOGGER.info("EmptyStackException: " + e.getMessage() + "\r\n" + str);
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
        LOGGER.info("DisconnectedException: " + e.getMessage() + "\r\n" + str);
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
        LOGGER.info("ReflectiveOperationException: " + e.getMessage() + "\r\n"
                + str);
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
        LOGGER.info("NotAnHatchException: " + e.getMessage() + "\r\n" + str);
    }

    /**
     * Warning logger.
     *
     * @param string
     *            the message
     */
    public static void warning(String string) {
        LOGGER.warning(string);
    }

    /**
     * JAXBE exception logger.
     *
     * @param e
     *            the exception
     */
    public static void jAXBException(JAXBException e) {
        LOGGER.info(e.getMessage());
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
        LOGGER.info("FileNotFoundException: " + e.getMessage() + "\r\n"
                + string);
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
        LOGGER.info("IOException: " + e.getMessage() + "\r\n" + string);
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
        LOGGER.info("Exception: " + ex.getMessage() + "\r\n" + string);
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
        LOGGER.info("RemoteException: " + e.getMessage() + "\r\n" + string);
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
        LOGGER.info("InvalidParameterException: " + ex.getMessage() + "\r\n"
                + string);
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
        LOGGER.info("InterruptedException: " + e.getMessage() + "\r\n" + string);
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
        LOGGER.info("IllegalArgumentException: " + ex.getMessage() + "\r\n"
                + string);
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
        LOGGER.info("UnsupportedOperationException: " + ex.getMessage()
                + "\r\n" + string);
    }

}
