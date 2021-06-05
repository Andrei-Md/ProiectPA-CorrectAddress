package com.example.springproject.logData;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class EventLog implements LogInterface {

    private static final String FILE_PATH = "./resources/logs/logEvent.log";

    public void printLogger(String logString) {
        RandomAccessFile fileWriter = null;
        try {
            fileWriter = new RandomAccessFile(FILE_PATH, "rw");
            append(logString, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * method used to append data to existing file
     *
     * @param string     data to append
     * @param fileWriter the Random access file
     * @throws IOException exception
     */
    private void append(String string, RandomAccessFile fileWriter) throws IOException {
        fileWriter.seek(fileWriter.length());
        writeInChannel(string, fileWriter);
    }

    /**
     * method used to write a sequence of bytes to the channel from the given string.
     *
     * @param string     the data to write in channel
     * @param fileWriter the random access file
     * @throws IOException exception
     */
    private void writeInChannel(String string, RandomAccessFile fileWriter) throws IOException {
        FileChannel channel = fileWriter.getChannel();
        ByteBuffer buffer = prepareBuffer(string.getBytes());
        channel.write(buffer);
        channel.close();
    }

    /**
     * method used to prepare the byte buffer
     *
     * @param bytes bytes to put in buffer
     * @return the byte buffer
     */
    private ByteBuffer prepareBuffer(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

}
