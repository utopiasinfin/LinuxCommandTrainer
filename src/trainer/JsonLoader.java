package trainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads LinuxCommand objects from the bundled resources/commands.json file.
 * Uses a built-in minimal JSON parser — no external dependencies required.
 */
public class JsonLoader {

    /** Load all commands from the classpath resource. */
    @SuppressWarnings("unchecked")
    public static List<LinuxCommand> loadCommands() {
        InputStream is = JsonLoader.class.getResourceAsStream("/resources/commands.json");
        if (is == null) {
            System.err.println("[JsonLoader] WARNING: resources/commands.json not found on classpath.");
            return new ArrayList<>();
        }
        String json;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            json = buffer.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            System.err.println("[JsonLoader] Error reading commands.json: " + e.getMessage());
            return new ArrayList<>();
        }

        SimpleJsonParser parser = new SimpleJsonParser(json);
        Map<String, Object> root = (Map<String, Object>) parser.parse();
        if (root == null || !root.containsKey("commands")) {
            System.err.println("[JsonLoader] No 'commands' key found in JSON.");
            return new ArrayList<>();
        }
        List<Object> commandsRaw = (List<Object>) root.get("commands");

        List<LinuxCommand> commands = new ArrayList<>();
        for (Object cmdObj : commandsRaw) {
            try {
                commands.add(parseCommand((Map<String, Object>) cmdObj));
            } catch (Exception e) {
                System.err.println("[JsonLoader] Skipping command due to parse error: " + e.getMessage());
            }
        }
        return commands;
    }

    @SuppressWarnings("unchecked")
    private static LinuxCommand parseCommand(Map<String, Object> cmd) {
        String id = str(cmd.get("id"));
        String command = str(cmd.get("command"));
        String syntax = str(cmd.get("syntax"));
        String diffStr = str(cmd.get("difficulty")).toUpperCase();
        Difficulty difficulty = Difficulty.valueOf(diffStr);

        // distros
        List<String> distros = new ArrayList<>();
        Object distrosObj = cmd.get("distros");
        if (distrosObj instanceof List) {
            for (Object d : (List<Object>) distrosObj) {
                distros.add(str(d));
            }
        }

        // explain
        Map<String, Object> explain = (Map<String, Object>) cmd.get("explain");
        Map<String, Object> enMap = (Map<String, Object>) explain.get("en");
        Map<String, Object> deMap = (Map<String, Object>) explain.get("de");
        String enShort = str(enMap.get("short"));
        String enLong = str(enMap.get("long"));
        String deShort = str(deMap.get("short"));
        String deLong = str(deMap.get("long"));

        // flags
        List<LinuxCommand.Flag> flags = new ArrayList<>();
        Object flagsObj = cmd.get("flags");
        if (flagsObj instanceof List) {
            for (Object f : (List<Object>) flagsObj) {
                Map<String, Object> fm = (Map<String, Object>) f;
                flags.add(new LinuxCommand.Flag(str(fm.get("flag")), str(fm.get("en")), str(fm.get("de"))));
            }
        }

        // examples
        List<LinuxCommand.Example> examples = new ArrayList<>();
        Object examplesObj = cmd.get("examples");
        if (examplesObj instanceof List) {
            for (Object e : (List<Object>) examplesObj) {
                Map<String, Object> em = (Map<String, Object>) e;
                examples.add(new LinuxCommand.Example(str(em.get("cmd")), str(em.get("en")), str(em.get("de"))));
            }
        }

        // pitfalls
        List<LinuxCommand.Pitfall> pitfalls = new ArrayList<>();
        Object pitfallsObj = cmd.get("pitfalls");
        if (pitfallsObj instanceof List) {
            for (Object p : (List<Object>) pitfallsObj) {
                Map<String, Object> pm = (Map<String, Object>) p;
                pitfalls.add(new LinuxCommand.Pitfall(str(pm.get("en")), str(pm.get("de"))));
            }
        }

        // accepted answers
        List<String> accepted = new ArrayList<>();
        Object acceptedObj = cmd.get("accepted");
        if (acceptedObj instanceof List) {
            for (Object a : (List<Object>) acceptedObj) {
                accepted.add(str(a));
            }
        }
        if (accepted.isEmpty()) {
            accepted.add(command);
        }

        return new LinuxCommand(id, command, syntax,
                enShort, enLong, deShort, deLong,
                flags, examples, pitfalls, accepted, distros, difficulty);
    }

    private static String str(Object o) {
        return o == null ? "" : o.toString();
    }

    // -------------------------------------------------------------------------
    // Minimal JSON parser — handles objects, arrays, strings, numbers, booleans
    // -------------------------------------------------------------------------
    static class SimpleJsonParser {
        private final String input;
        private int pos;

        SimpleJsonParser(String input) {
            this.input = input;
            this.pos = 0;
        }

        Object parse() {
            skipWhitespace();
            if (pos >= input.length()) return null;
            char c = input.charAt(pos);
            if (c == '{') return parseObject();
            if (c == '[') return parseArray();
            if (c == '"') return parseString();
            if (c == 't' || c == 'f') return parseBoolean();
            if (c == 'n') {
                if (pos + 4 <= input.length() && input.startsWith("null", pos)) {
                    pos += 4;
                } else {
                    pos++; // skip unknown character gracefully
                }
                return null;
            }
            return parseNumber();
        }

        private Map<String, Object> parseObject() {
            Map<String, Object> map = new LinkedHashMap<>();
            pos++; // skip '{'
            skipWhitespace();
            if (pos < input.length() && input.charAt(pos) == '}') {
                pos++;
                return map;
            }
            while (pos < input.length()) {
                skipWhitespace();
                String key = parseString();
                skipWhitespace();
                if (pos < input.length() && input.charAt(pos) == ':') pos++;
                skipWhitespace();
                Object value = parse();
                map.put(key, value);
                skipWhitespace();
                if (pos < input.length() && input.charAt(pos) == ',') {
                    pos++;
                } else {
                    break;
                }
            }
            skipWhitespace();
            if (pos < input.length() && input.charAt(pos) == '}') pos++;
            return map;
        }

        private List<Object> parseArray() {
            List<Object> list = new ArrayList<>();
            pos++; // skip '['
            skipWhitespace();
            if (pos < input.length() && input.charAt(pos) == ']') {
                pos++;
                return list;
            }
            while (pos < input.length()) {
                skipWhitespace();
                Object value = parse();
                list.add(value);
                skipWhitespace();
                if (pos < input.length() && input.charAt(pos) == ',') {
                    pos++;
                } else {
                    break;
                }
            }
            skipWhitespace();
            if (pos < input.length() && input.charAt(pos) == ']') pos++;
            return list;
        }

        private String parseString() {
            pos++; // skip opening '"'
            StringBuilder sb = new StringBuilder();
            while (pos < input.length()) {
                char c = input.charAt(pos);
                if (c == '"') {
                    pos++;
                    break;
                }
                if (c == '\\' && pos + 1 < input.length()) {
                    pos++;
                    char esc = input.charAt(pos);
                    switch (esc) {
                        case '"':  sb.append('"');  break;
                        case '\\': sb.append('\\'); break;
                        case '/':  sb.append('/');  break;
                        case 'n':  sb.append('\n'); break;
                        case 'r':  sb.append('\r'); break;
                        case 't':  sb.append('\t'); break;
                        default:   sb.append(esc);
                    }
                } else {
                    sb.append(c);
                }
                pos++;
            }
            return sb.toString();
        }

        private Boolean parseBoolean() {
            if (pos + 4 <= input.length() && input.startsWith("true", pos)) {
                pos += 4;
                return Boolean.TRUE;
            }
            pos += 5; // "false"
            return Boolean.FALSE;
        }

        private Number parseNumber() {
            int start = pos;
            while (pos < input.length() && "-0123456789.eE+".indexOf(input.charAt(pos)) >= 0) {
                pos++;
            }
            String numStr = input.substring(start, pos);
            if (numStr.contains(".") || numStr.contains("e") || numStr.contains("E")) {
                return Double.parseDouble(numStr);
            }
            return Long.parseLong(numStr);
        }

        private void skipWhitespace() {
            while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
                pos++;
            }
        }
    }
}
