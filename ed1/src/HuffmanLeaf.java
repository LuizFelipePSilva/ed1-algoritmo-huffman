public class HuffmanLeaf extends HuffmanTree {
    public final char value;

    public HuffmanLeaf(int freq, char value) {
        super(freq);
        this.value = value;
    }
}
