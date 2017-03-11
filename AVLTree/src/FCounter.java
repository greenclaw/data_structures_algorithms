
import structure.avl.internal.AVL;

import java.io.IOException;
import java.util.function.BiConsumer;

public class FCounter {
    Reader reader;
    Writer writer;
    AVL<Character,Integer> tree;

    FCounter(String input, String output) throws IOException {
        tree = new AVL();
        reader = new Reader(input);
        writer = new Writer(output);
    }

    // store chars and their frequency in tree
    // key - character  and value - is frequency of this character
    public void storeChars() throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            line = line.trim().toLowerCase().replaceAll(" ", "");
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                Integer v = tree.get(c);
                if (v == null)
                    tree.put(c, 1);
                else
                    tree.put(c, ++v);
            }
        }

    }

    // remove from map symbols 0x21-0x30, 0x3A-0x40, 0x5B-0x60
    public void removeSymbols() {
        tree.delete('.');
        for (int i = 33; i < 48; i++) {
            tree.delete((char)i);
        }

        for (int i = 58; i < 64; i++) {
            tree.delete((char)i);
        }

        for (int i = 91; i < 96; i++) {
            tree.delete((char)i);
        }
    }

    public void writeFrequency() throws IOException {
        BiConsumer<Character, Integer> visitor = (key, value) -> {
            try {
                writer.write(key + ":" + value + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        tree.traverse(visitor);
        writer.writer.close();
    }
}
