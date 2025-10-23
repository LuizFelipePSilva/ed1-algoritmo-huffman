import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        // Usando palavras para testar o algortimo de huffman
        String test = "paralelepipedo"; // Essa frase tem 112 bits pois 14 palavras cada uma com 8 bits

        int[] charFrequency = new int[256];
        for(char c: test.toCharArray()) {
            charFrequency[c]++;
        }

        HuffmanTree tree = BuildTree(charFrequency);
        printCodes(tree, new StringBuffer());
        String encode = encode(tree,test);
        System.out.println(encode);
        String decode = decode(tree,encode);
        System.out.println(decode);
    }

    public static HuffmanTree BuildTree(int[] charFrequency) {
        //Cria uma fila de prioridade
        //Fila será pela ordem de frequência no texto
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        //Cria as folhas das Arvores para cada letra
        for(int i = 0; i < charFrequency.length; i++) {
            if(charFrequency[i] > 0) {
                trees.offer(new HuffmanLeaf(charFrequency[i], (char)i));
            }
        }
        while(trees.size() > 1) {
            HuffmanTree left = trees.poll();
            HuffmanTree right = trees.poll();

            trees.offer(new HuffmanNode(left, right));
        }
        return trees.poll();
    }

    public static String encode(HuffmanTree tree, String encoding) {
        assert tree != null;

        String encodeText = "";
        for(char c: encoding.toCharArray()) {
            encodeText+=(getCodes(tree, new StringBuffer(), c));
        }
        return encodeText;

    }

    public static String getCodes(HuffmanTree tree, StringBuffer buffer, char c) {
        assert tree != null;
        if(tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            if (leaf.value == c) {
                return buffer.toString();
            }
        }
        else if(tree instanceof HuffmanNode) {
                HuffmanNode node = (HuffmanNode) tree;

                buffer.append('0');
                String left = getCodes(node.left, buffer, c);
                buffer.deleteCharAt(buffer.length() - 1);

                buffer.append('1');
                String right = getCodes(node.right, buffer, c);
                buffer.deleteCharAt(buffer.length() - 1);

                if(left == null) return right; else return left;
            }
            return null;
        }

        public static void printCodes(HuffmanTree tree, StringBuffer buffer) {
            assert tree != null;
            if(tree instanceof HuffmanLeaf) {
                HuffmanLeaf leaf = (HuffmanLeaf) tree;

                System.out.println(leaf.value + "\t" + leaf.frequency + "\t\t" + buffer);
            }
            else if(tree instanceof HuffmanNode) {
                HuffmanNode node = (HuffmanNode) tree;

                buffer.append('0');
                printCodes(node.left, buffer);
                buffer.deleteCharAt(buffer.length() - 1);

                buffer.append('1');
                printCodes(node.right, buffer);
                buffer.deleteCharAt(buffer.length() - 1);

            }
        }

        public static String decode(HuffmanTree tree, String encoding) {
            assert tree != null;
            String decodeText = "";
            HuffmanNode node = (HuffmanNode) tree;
            for (char code : encoding.toCharArray()) {
                if (code == '0') {
                    if (node.left instanceof HuffmanLeaf) {
                        decodeText += ((HuffmanLeaf) node.left).value;
                        node = (HuffmanNode) tree;
                    } else {
                        node = (HuffmanNode) node.left;
                    }
                } else if (code == '1') {
                    if (node.right instanceof HuffmanLeaf) {
                        decodeText += ((HuffmanLeaf) node.right).value;
                        node = (HuffmanNode) tree;
                    } else {
                        node = (HuffmanNode) node.right;
                    }
                }
            }
            return decodeText;
        }

}