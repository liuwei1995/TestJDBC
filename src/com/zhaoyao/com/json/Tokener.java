package com.zhaoyao.com.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;


public class Tokener {
	private long character;
	private boolean eof;
	private long index;
	private long line;
	private char previous;
	private char upPrevious;
	private Reader reader;
	private boolean usePrevious;
	protected final  Stack<Character> sc=new Stack<Character>();
	public Tokener(Reader reader) {
		this.reader = (reader.markSupported() ? reader : new BufferedReader(
				reader));
		this.eof = false;
		this.usePrevious = false;
		this.previous = '\000';
		this.index = 0L;
		this.character = 1L;
		this.line = 1L;
	}

	public Tokener(InputStream inputStream) throws RuntimeException {
		this(new InputStreamReader(inputStream));
	}

	public Tokener(String s) {
		this(new StringReader(s));
	}

	public String nextString(char quote) throws RuntimeException {
		StringBuffer sb = new StringBuffer();
		while (true) {
			char c = next();
			switch (c) {
			case '\000':

			case '\n':

			case '\r':
				throw new RuntimeException("Unterminated string");
			case '\\':
				c = next();
				switch (c) {
				case 'b':
					sb.append('\b');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'f':
					sb.append('\f');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 'u':
					sb.append((char) Integer.parseInt(next(4), 16));
					break;
				case '"':

				case '\'':

				case '/':

				case '\\':
					sb.append(c);
					break;
				default:
					throw new RuntimeException("Illegal escape.");
				}

			default:
				if (c == quote) {
					return sb.toString();
				}
				sb.append(c);
			}
		}
	}

	public char next() throws RuntimeException {
		int c;
		upPrevious = previous;
		if (this.usePrevious) {
			this.usePrevious = false;
			c = this.previous;
		} else {
			try {
				c = this.reader.read();
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}

			if (c <= 0) {
				this.eof = true;
				c = 0;
			}
		}
		this.index += 1L;
		if (this.previous == '\r') {
			this.line += 1L;
			this.character = (c == 10 ? 0 : 1);
		} else if (c == 10) {
			this.line += 1L;
			this.character = 0L;
		} else {
			this.character += 1L;
		}
		this.previous = (char) c;
		return this.previous;
	}

	public String next(int n) throws RuntimeException {
		if (n == 0) {
			return "";
		}

		char[] chars = new char[n];
		int pos = 0;

		while (pos < n) {
			chars[pos] = next();
			if (end()) {
				throw new RuntimeException("Substring bounds error");
			}
			pos++;
		}
		return new String(chars);
	}

	public Object nextValue() throws RuntimeException {
		char c = nextClean();

		switch (c) {
		case '"':
		case '\'':
			return nextString(c);
		case '{':
			back();
			return new JSONObject(this);
		case '[':
			back();
//			return new JSONArray(this);
		}

		StringBuffer sb = new StringBuffer();
		while ((c >= ' ') && (",:]}/\\\"[{;=#".indexOf(c) < 0)) {
			sb.append(c);
			c = next();
		}
		back();

		String string = sb.toString().trim();
		if ("".equals(string)) {
			throw new RuntimeException("Missing value");
		}
		return JSONObject.stringToValue(string);
	}
	public Object nextValue(char c) throws RuntimeException {
		switch (c) {
		case '"':
		case '\'':
			return nextString(c);
		case '{':
			back();
			return new JSONObject(this);
		case '[':
			back();
//			return new JSONArray(this);
		}
		
		StringBuffer sb = new StringBuffer();
		while ((c >= ' ') && (",:]}/\\\"[{;=#".indexOf(c) < 0)) {
			sb.append(c);
			c = next();
		}
		back();
		
		String string = sb.toString().trim();
		if ("".equals(string)) {
			throw new RuntimeException("Missing value");
		}
		return JSONObject.stringToValue(string);
	}

	public char nextClean() throws RuntimeException {
		char c;
		do
			c = next();
		while ((c != 0) && (c <= ' '));
		return c;
	}

	public void back() throws RuntimeException {
		if ((this.usePrevious) || (this.index <= 0L)) {
			throw new RuntimeException("Stepping back two steps is not supported");
		}
		this.index -= 1L;
		this.character -= 1L;
		this.usePrevious = true;
		this.eof = false;
	}

	public char getPrevious() {
		return previous;
	}

	public void setPrevious(char previous) {
		this.previous = previous;
	}

	public char getUpPrevious() {
		return upPrevious;
	}

	public void setUpPrevious(char upPrevious) {
		this.upPrevious = upPrevious;
	}

	public boolean end() {
		return (this.eof) && (!this.usePrevious);
	}
}
