package com.zhaoyao.com.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

public class JSONObject {
	private final Map map;

	public JSONObject() {
		this.map = new HashMap();
	}

	public JSONObject(String source) throws RuntimeException {
		this(new Tokener(source));
	}

	public JSONObject(Tokener x) throws RuntimeException {
		this();
		if (x.next() != '{')
			throw new RuntimeException("A JSONObject text must begin with '{'");
		pase(x);
	}

	@SuppressWarnings("unused")
	public void pase(Tokener x) {
		while (true) {
			char c = x.next();
			switch (c) {
			case '\000':
				throw new RuntimeException(
						"A JSONObject text must end with '}'");
			case '}':
				return;
			}
			String key = null;
			s1: while (true) {
				switch (x.getPrevious()) {
				case '"':
				case '\'':
					key = x.nextValue(x.getPrevious()).toString();
					c = x.next();
					if (c != ':') {
						throw new RuntimeException("Expected a ':' after a key");
					}
					putOnce(key, x.nextValue());
					break s1;
				case ',':
					char upPrevious = x.getUpPrevious();
					if (upPrevious == ',' || upPrevious == '"')
						break s1;
					char next = x.next();
					switch (next) {
					case '"':
						continue s1;
					case '{':
						throw new RuntimeException(" {{ 格式错误");
					case '[':
						throw new RuntimeException(" ,[ 格式错误");
					default:
						break;
					}
					break s1;
				case '{':
					throw new RuntimeException(" {{ 格式错误");
				case '[':
					throw new RuntimeException(" {{ 格式错误");

				default:
					break s1;
				}
			}
		}
	}

	public JSONObject putOnce(String key, Object value) throws RuntimeException {
		if ((key != null) && (value != null)) {
			if (opt(key) != null) {
				throw new RuntimeException("Duplicate key \"" + key + "\"");
			}
			put(key, value);
		}
		return this;
	}

	public Object opt(String key) {
		return key == null ? null : this.map.get(key);
	}

	public JSONObject put(String key, Object value) throws RuntimeException {
		if (key == null) {
			throw new NullPointerException("Null key.");
		}
		if (value != null) {
			testValidity(value);
			this.map.put(key, value);
		} else {
			remove(key);
		}
		return this;
	}

	public static void testValidity(Object o) throws RuntimeException {
		if (o != null)
			if ((o instanceof Double)) {
				if ((((Double) o).isInfinite()) || (((Double) o).isNaN()))
					throw new RuntimeException(
							"JSON does not allow non-finite numbers.");
			} else if (((o instanceof Float))
					&& ((((Float) o).isInfinite()) || (((Float) o).isNaN())))
				throw new RuntimeException(
						"JSON does not allow non-finite numbers.");
	}

	public Object remove(String key) {
		return this.map.remove(key);
	}

	public static Object stringToValue(String string) {
		if (string.equals("")) {
			return string;
		}
		if (string.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		}
		if (string.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		}
		if (string.equalsIgnoreCase("null")) {
			return null;
		}

		char b = string.charAt(0);
		if (((b >= '0') && (b <= '9')) || (b == '-'))
			try {
				if ((string.indexOf('.') > -1) || (string.indexOf('e') > -1)
						|| (string.indexOf('E') > -1)) {
					Double d = Double.valueOf(string);
					if ((!d.isInfinite()) && (!d.isNaN()))
						return d;
				} else {
					Long myLong = new Long(string);
					if (string.equals(myLong.toString())) {
						if (myLong.longValue() == myLong.intValue()) {
							return new Integer(myLong.intValue());
						}
						return myLong;
					}
				}
			} catch (Exception localException) {
			}
		return string;
	}
	   public Object get(String key) throws JSONException {
		   if (key == null) { 
			   throw new JSONException("Null key."); 
			   }
		   Object object = opt(key);
		   if (object == null) {
			   throw new JSONException("JSONObject[" + quote(key) + "] not found.");
			   }
		   return object;
		   }
	public String toString() {
		try {
			return toString(0);
		} catch (Exception e) {
		}
		return null;
	}

	public String toString(int indentFactor) throws Exception {
		StringWriter w = new StringWriter();
		synchronized (w.getBuffer()) {
			return write(w, indentFactor, 0).toString();
		}
	}

	Writer write(Writer writer, int indentFactor, int indent) throws Exception {
		try {
			boolean commanate = false;
			int length = length();
			Iterator keys = keys();
			writer.write(123);

			if (length == 1) {
				Object key = keys.next();
				writer.write(quote(key.toString()));
				writer.write(58);
				if (indentFactor > 0) {
					writer.write(32);
				}
				writeValue(writer, this.map.get(key), indentFactor, indent);
			} else if (length != 0) {
				int newindent = indent + indentFactor;
				while (keys.hasNext()) {
					Object key = keys.next();
					if (commanate) {
						writer.write(44);
					}
					if (indentFactor > 0) {
						writer.write(10);
					}
					indent(writer, newindent);
					writer.write(quote(key.toString()));
					writer.write(58);
					if (indentFactor > 0) {
						writer.write(32);
					}
					writeValue(writer, this.map.get(key), indentFactor,
							newindent);
					commanate = true;
				}
				if (indentFactor > 0) {
					writer.write(10);
				}
				indent(writer, indent);
			}
			writer.write(125);
			return writer;
		} catch (IOException exception) {
			throw new Exception(exception);

		}
	}

	public int length() {
		return this.map.size();
	}

	public Iterator keys() {
		return keySet().iterator();
	}

	public Set keySet() {
		return this.map.keySet();
	}

	public static String quote(String string) {
		StringWriter sw = new StringWriter();
		synchronized (sw.getBuffer()) {
			try {
				return quote(string, sw).toString();
			} catch (IOException ignored) {
				return "";
			}
		}
	}

	static final void indent(Writer writer, int indent) throws IOException {
		for (int i = 0; i < indent; i++)
			writer.write(32);
	}

	public static Writer quote(String string, Writer w) throws IOException {
		if ((string == null) || (string.length() == 0)) {
			w.write("\"\"");
			return w;
		}

		char c = '\000';

		int len = string.length();

		w.write(34);
		for (int i = 0; i < len; i++) {
			char b = c;
			c = string.charAt(i);
			switch (c) {
			case '"':
			case '\\':
				w.write(92);
				w.write(c);
				break;
			case '/':
				if (b == '<') {
					w.write(92);
				}
				w.write(c);
				break;
			case '\b':
				w.write("\\b");
				break;
			case '\t':
				w.write("\\t");
				break;
			case '\n':
				w.write("\\n");
				break;
			case '\f':
				w.write("\\f");
				break;
			case '\r':
				w.write("\\r");
				break;
			default:
				if ((c < ' ') || ((c >= '') && (c < ' '))
						|| ((c >= ' ') && (c < '℀'))) {
					w.write("\\u");
					String hhhh = Integer.toHexString(c);
					w.write("0000", 0, 4 - hhhh.length());
					w.write(hhhh);
				} else {
					w.write(c);
				}
			}
		}
		w.write(34);
		return w;
	}
	public static String numberToString(Number number)
			/*      */     throws JSONException
			/*      */   {
			/*  768 */     if (number == null) {
			/*  769 */       throw new JSONException("Null pointer");
			/*      */     }
			/*  771 */     testValidity(number);
			/*      */ 
			/*  775 */     String string = number.toString();
			/*  776 */     if ((string.indexOf('.') > 0) && (string.indexOf('e') < 0) && 
			/*  777 */       (string.indexOf('E') < 0)) {
			/*  778 */       while (string.endsWith("0")) {
			/*  779 */         string = string.substring(0, string.length() - 1);
			/*      */       }
			/*  781 */       if (string.endsWith(".")) {
			/*  782 */         string = string.substring(0, string.length() - 1);
			/*      */       }
			/*      */     }
			/*  785 */     return string;
			/*      */   }
	static final Writer writeValue(Writer writer, Object value,
			int indentFactor, int indent) throws Exception, IOException {
		if ((value == null) || (value.equals(null))) {
			writer.write("null");
		} else if ((value instanceof JSONObject)) {
			((JSONObject) value).write(writer, indentFactor, indent);
		}
		// else if ((value instanceof JSONArray)) {
		// ((JSONArray)value).write(writer, indentFactor, indent);
		// }
		else if ((value instanceof Map)) {
			// new JSONObject((Map)value).write(writer, indentFactor, indent);
		} else if ((value instanceof Collection)) {
			// new JSONArray((Collection)value).write(writer, indentFactor,
			// indent);
		} else if (value.getClass().isArray()) {
			// new JSONArray(value).write(writer, indentFactor, indent);
		} else if ((value instanceof Number)) {
			 writer.write(numberToString((Number)value));
		} else if ((value instanceof Boolean)) {
			writer.write(value.toString());
		}
//		 else if ((value instanceof JSONString))
//		 {
//		 try {
//		 o = ((JSONString)value).toJSONString();
//		 }
//		 catch (Exception e)
//		 {
//		 Object o;
//		 throw new Exception(e);
//		 }
//		 Object o;
//		 writer.write(o != null ? o.toString() : quote(value.toString()));
//		 } 
		 else {
		 quote(value.toString(), writer);
		 }
		return writer;
	}

}
