package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public static void main(String[] args) {
        ListNode firstL1 = new ListNode(2);
        ListNode firstL2 = new ListNode(5);

        ListNode l1 = firstL1;
        ListNode l2 = firstL2;

        l1.next = new ListNode(4);
        l1 = l1.next;
        l2.next = new ListNode(6);
        l2 = l2.next;

        l1.next = new ListNode(9);
        l1 = l1.next;
        l2.next = new ListNode(4);
        l2 = l2.next;

        l1.next = new ListNode(9);


//        l2.next = new ListNode(4);


        ListNode node = addTwoNumbers(firstL1, firstL2);

        while (node.next != null){
            System.out.println(node.val);
            node = node.next;
        }
        System.out.println(node.val);

    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode pointerL1 = l1, pointerL2 = l2, currentNode = result;
        int carry = 0;

        while (pointerL1 != null || pointerL2 != null){
            int pointerL1Valur = (pointerL1 == null)? 0 : pointerL1.val;
            int pointerL2Valur = (pointerL2 == null)? 0 : pointerL2.val;

            int sum = pointerL1Valur + pointerL2Valur + carry;
            carry = sum / 10;
            currentNode.next = new ListNode(sum % 10);
            currentNode = currentNode.next;

            if (pointerL1 != null){
                pointerL1 = pointerL1.next;
            }

            if (pointerL2 != null){
                pointerL2 = pointerL2.next;
            }

        }

        if (carry > 0){
            currentNode.next = new ListNode(carry);
        }

        return result.next;
    }

}