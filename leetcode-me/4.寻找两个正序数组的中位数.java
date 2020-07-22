/*
 * @lc app=leetcode.cn id=4 lang=java
 *
 * [4] 寻找两个正序数组的中位数
 *
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/description/
 *
 * algorithms
 * Hard (38.39%)
 * Likes:    2920
 * Dislikes: 0
 * Total Accepted:    225.1K
 * Total Submissions: 586.4K
 * Testcase Example:  '[1,3]\n[2]'
 *
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * 
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * 
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * 
 * 
 * 
 * 示例 1:
 * 
 * nums1 = [1, 3]
 * nums2 = [2]
 * 
 * 则中位数是 2.0
 * 
 * 
 * 示例 2:
 * 
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * 
 * 则中位数是 (2 + 3)/2 = 2.5
 * 
 * 
 */

// @lc code=start
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int index1=0,index2=0;
        int m1=0,m2=0;
        for ( int i = 0; i <= (nums2.length+nums1.length)/2; i++) {
            m1=m2;
            if(index1 == nums1.length){
                m2=nums2[index2];
                index2++;
            }else if(index2 == nums2.length){
                m2= nums1[index1];
                index1++;
            }else if(nums1[index1]<nums2[index2]){
                m2 = nums1[index1];
                index1++;
            }else{
                m2=nums2[index2];
                index2++;
            }
        }
        if((nums1.length+nums2.length)%2 ==0){
            return (double)(m1+m2)/2;
        }

        return m2;
    }
}
// @lc code=end

