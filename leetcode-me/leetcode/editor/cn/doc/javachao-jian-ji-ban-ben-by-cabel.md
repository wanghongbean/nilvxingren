Java超简洁版本:
解题思路: 链表结构非空且为逆序，相对于对其个位数，所以直接链表相加并留意进位即可获得结果值链表
注意点:
1.进位问题，需要一个carry变量保存下一个结点的进位
2.链表长度问题，需要做非空判断
```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        int carry = 0;
        ListNode cur = dummy;

        while (l1 != null || l2 != null || carry > 0){
            cur.next = new ListNode();
            cur = cur.next;

            if (l1 != null ){
                 carry += l1.val;
                 l1 = l1.next;
            }
             if (l2 != null ){
                 carry += l2.val;
                 l2 = l2.next;
            }

            cur.val = carry %10;
            carry = carry/10;

        }
        return dummy.next;

    }
}
```
