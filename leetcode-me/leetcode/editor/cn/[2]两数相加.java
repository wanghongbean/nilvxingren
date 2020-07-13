//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。 
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。 
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 示例： 
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
// 
// Related Topics 链表 数学 
// 👍 4582 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    //链表逆序  进位问题
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode c = res;
        int p = 0;
        while (null != l1 || null != l2 || p > 0) {
            int x = null == l1 ? 0 : l1.val;
            int y = null == l2 ? 0 : l2.val;
            int sum = x + y + p;
            int s = sum % 10;
            c.next = new ListNode(s);
            p = sum / 10;
            if (null != l1) {
                l1 = l1.next;
            }
            if (null != l2) {
                l2 = l2.next;
            }
            c = c.next;
        }
        return res.next;
    }


    //独特想法，但没成功
//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        List<Integer> ll1 = new ArrayList();
//        List<Integer> ll2 = new ArrayList();
//        ll1.add(l1.val);
//
//        do {
//            l1 = l1.next;
//            if(null != l1){
//                ll1.add(l1.val);
//            }
//        } while(null != l1 && l1.next != null);
//
//        ll2.add(l2.val);
//
//        do {
//            l2 = l2.next;
//            if(null != l2){
//                ll2.add(l2.val);
//            }
//        } while(null != l2 && l2.next != null);
//
//        int n1 = 0;
//
//        int n2;
//        for(n2 = 0; n2 < ll1.size(); ++n2) {
//            n1 += (Integer)ll1.get(n2) * (int)Math.pow(10.0D, (double)n2);
//        }
//
//        n2 = 0;
//
//        int n;
//        for(n = 0; n < ll2.size(); ++n) {
//            n2 += (Integer)ll2.get(n) * (int)Math.pow(10.0D, (double)n);
//        }
//
//        n = n1 + n2;
//        String s = String.valueOf(n);
//        List<ListNode> nodes = new ArrayList();
//
//        int i;
//        for(i = 0; i < s.length(); ++i) {
//            ListNode listNode = new ListNode(Character.getNumericValue(s.charAt(i)));
//            nodes.add(listNode);
//        }
//
//        for(i = nodes.size() - 1; i > 0; --i) {
//            ((ListNode)nodes.get(i)).next = (ListNode)nodes.get(i - 1);
//        }
//
//        return (ListNode)nodes.get(nodes.size() - 1);
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
