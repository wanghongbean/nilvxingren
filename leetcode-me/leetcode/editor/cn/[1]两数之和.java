//给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。 
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。 
//
// 
//
// 示例: 
//
// 给定 nums = [2, 7, 11, 15], target = 9
//
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]
// 
// Related Topics 数组 哈希表 
// 👍 8611 👎 0


import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        //时间复杂度 O(n^2) 空间复杂度 O(1)
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i+1; j < nums.length; j++) {
//                if ((nums[i] + nums[j])==target) {
//                    return new int[]{i,j};
//                }
//            }
//        }
//        throw new IllegalArgumentException("no two sum solution");

        //时间复杂度 O(n) 空间复杂度 O(n)
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int ep = target - nums[i];
            if (map.containsKey(ep) && ep != nums[i]){
                return new int[]{i,map.get(ep)};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("no two sum solution");
    }
}
//leetcode submit region end(Prohibit modification and deletion)
