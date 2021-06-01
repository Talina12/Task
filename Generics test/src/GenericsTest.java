public class GenericsTest {
    public static void main(String[] args) {
        //JenBox<Basic> JenObject = new JenBox<InheritBasic>();
        Basic basicobject = new InheritBasic("basicObject");
        int i = 42;
        String s1 = "dsdfhbncfhmgjmfghgzfsdhcfhmgmdfhgfxgzfgxncmfhmgj,gmgzfgtytrhhkjhjhjhkjhkjhkjhkjhkjgjhghjghfghhjkhkjhkjhjgjhgkjhkhkgjhgjhgjhgjryjkmtj,jg,mghm";
        String s2 = "dsdfhbncfhmgjmfghgzfsdhcfhmgmdfhgfxgzfgxncmfhmgj,gmgzfgtytrhhkjhjhjhkjhkjhkjhkjhkjgjhghjghfghhjkhkjhkjhjgjhgkjhkhkgjhgjhgjhgjryjkmtj,jg,mghm";
        System.out.println(s1 ==s2);
    }
}
