package os;

import java.io.OutputStream;
import java.io.IOException;
    
//
// (c) 2000-2001 david croft. please see the license at:
//
// http://zooid.org/~tforcdivad/license.txt
//

/**
 * OutputStream for printing to a TextArea.
 *
 * @see TextArea
 * @see TextAreaInputStream
 */
public class TextAreaOutputStream extends OutputStream {

    private int cnt = 0;
      // per distinguere l'aprrtura da dopo

	private TextArea textArea_;
	char[] buffer_;
	private final int BUFFERSIZE = 100;
	int index_;
	
	/**
	 * Create a new stream that will write buffered output to a TextArea.
     * @param textArea the text area
	 */
	TextAreaOutputStream(TextArea textArea) {
		textArea_ = textArea;
		buffer_ = new char[BUFFERSIZE];
		reset();
	}

	private void reset() {
		for (int i=0; i < buffer_.length; i++) {
			buffer_[i] = (char)0;
		}
		index_=0;
	}

	public void write(int b) throws IOException {
        char charCast = (char)b;
        if (index_ >= BUFFERSIZE) {
			flush();
		}
		buffer_[index_] = charCast;
		index_++;

		// if charCast is a newline or return character, flush the stream
		if (charCast == '\n' || charCast == '\r') {
			flush();
		}
	}

	public void flush() {

		// What if there is nothing to flush?
		if (index_ == 0) {
			return;
		}
		
		// add the current buffer to the textArea_
		//
		String string = new String(buffer_, 0, index_);
		addStringToTextArea(string);
		reset();
	}
	
	/**
	 * Close and flush() the stream.
	 */
	public void close() {
		flush();
	}
	
	private final void addStringToTextArea(String string) {

// errore con /r, patch M.Moro  2003-10-07
if (string.charAt(string.length()-1) == '\r')
{
// System.out.println("!!!! last CR removed!");
string = string.substring(0, string.length()-1);
}

		int length = string.length();
		int newPosition = textArea_.eolPosition_ + length;
		textArea_.insert(string, textArea_.eolPosition_);
		try {
			textArea_.setCaretPosition(newPosition);
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal argument in TextAreaOutputStream: ");
			e.printStackTrace(System.err);
		}
		textArea_.solPosition_ = newPosition;
		textArea_.eolPosition_ = newPosition;
    }
}
