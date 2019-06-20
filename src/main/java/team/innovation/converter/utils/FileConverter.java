package team.innovation.converter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * file converter interface
 *
 * @author bin.yan
 */
interface FileConverter {

    /**
     * convert to stream by stream,implementation class should override this method
     *
     * @param in
     * @param os
     */
    default void convert(InputStream in, OutputStream os) throws IOException {
        throw new RuntimeException("Converter didn't implemented this method");
    }

}
