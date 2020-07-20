import java.util.HashMap;

/*
 * @lc app=leetcode.cn id=3 lang=java
 *
 * [3] 无重复字符的最长子串
 */

// @lc code=start
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int l = s.length();
        int res = 0 ;
        HashMap<Character,Integer> map = new HashMap<>();
        for(int start=0,end=0;end<l;end++){
            char endChar = s.charAt(end);
            if(map.containsKey(endChar)){
                start = Math.max(map.get(endChar), start);
            }
            res = Math.max(res, end-start+1);
            map.put(endChar,end+1);
        }
        return res;
    }
}
// @lc code=end

