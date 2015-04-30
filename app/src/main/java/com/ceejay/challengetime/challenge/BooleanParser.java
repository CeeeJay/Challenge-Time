package com.ceejay.challengetime.challenge;

/**
 * Created by CJay on 25.04.2015 for Challenge Time.
 */
public class BooleanParser {
    public final static String TAG = BooleanParser.class.getSimpleName();

    public static String parse(final String str) {
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            String parse() {
                eatSpace();
                eatChar();
                return parseOr();
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term | expression `|` term
            // term = factor | term `*` factor | term `/` factor | term `&` factor | term brackets
            // factor = brackets | number | factor `^` factor | factor `<` factor | factor `>` factor | factor `=` factor
            // brackets = `(` expression `)`

            String parseOr() {
                String v = parseAnd();
                for (;;) {
                    eatSpace();
                    if (c == '|') { // subtraction
                        eatChar();
                        if (c == '|') eatChar();
                        eatSpace();
                        v = String.valueOf(Boolean.parseBoolean(v) || Boolean.parseBoolean(parseOr()));
                    }  else {
                        return v;
                    }
                }
            }

            String parseAnd() {
                String v = parseComp();
                for (;;) {
                    eatSpace();
                    if (c == '&') { // subtraction
                        eatChar();
                        if (c == '&') eatChar();
                        eatSpace();
                        v = String.valueOf(Boolean.parseBoolean(v) && Boolean.parseBoolean(parseOr()));
                    }else {
                        return v;
                    }
                }
            }

            String parseComp() {
                String v = parseExpression();
                eatSpace();
                if(c == '='){
                    eatChar();
                    if(c == '=') eatChar();
                    eatSpace();
                    return String.valueOf(new Double( v ).compareTo(Double.parseDouble(parseExpression())) == 0);
                }else if(c == '>'){
                    eatChar();eatSpace();
                    return String.valueOf(new Double( v ).compareTo(Double.parseDouble(parseExpression())) == 1);
                }else if(c == '<'){
                    eatChar();eatSpace();
                    return String.valueOf(new Double( v ).compareTo(Double.parseDouble(parseExpression())) == -1);
                }else {
                    return v;
                }
            }

            String parseExpression() {
                String v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v = String.valueOf(Double.parseDouble(v) + Double.parseDouble(parseTerm()));
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v = String.valueOf(Double.parseDouble(v) - Double.parseDouble(parseTerm()));
                    } else {
                        return v;
                    }
                }
            }

            String parseTerm() {
                String v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v = String.valueOf(Double.parseDouble(v) / Double.parseDouble(parseFactor()));
                    } else if (c == '*' || c == '(') { // multiplication
                        if (c == '*') eatChar();
                        v = String.valueOf(Double.parseDouble(v) * Double.parseDouble(parseFactor()));
                    } else {
                        return v;
                    }
                }
            }

            String parseFactor() {
                String v;
                boolean negate = false;
                eatSpace();
                if (c == '(') { // brackets
                    eatChar();
                    v = parseOr();
                    if (c == ')') eatChar();
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    if( c == 'f' || c == 't' ){
                        sb.append((char)c);eatChar();
                        sb.append((char)c);eatChar();
                        sb.append((char)c);eatChar();
                        sb.append((char)c);eatChar();
                        if( c == 'e'){sb.append((char)c);eatChar();}
                        eatSpace();
                    }else {
                        if (c == '+' || c == '-') { // unary plus & minus
                            negate = c == '-';
                            eatChar();
                            eatSpace();
                        }
                        while ((c >= '0' && c <= '9') || c == '.') {
                            sb.append((char) c);
                            eatChar();
                        }
                    }
                    if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                    v = sb.toString();
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = String.valueOf(Math.pow(Double.parseDouble(v), Double.parseDouble(parseFactor())));
                }
                if (negate) v = '-' + v;
                return v;
            }
        }

        return new Parser().parse();
    }


}




