package com.atguigu.p2p.test.shufa.sevensort;

import java.util.Arrays;

/**
 * @Auther: niujianye
 * @Date: 2018/11/27 16:59
 * @Description:
 * http://www.cnblogs.com/chengxiao/p/6129630.html
 * 总结堆排序的基本思路：
　　a.首先根据需求将无序序列按照完全二叉树的特点 从最后一个非叶子节点 开始调整，构建成大顶堆或小顶堆;
　　b.然后将堆顶元素与堆末尾元素交换，这样就将该锥顶元素筛选到了数组中最终的顺序位置;
　　c.再重新调整筛选出来以外的元素的数列结构，使其满足堆定义，然后继续交换堆顶元素与当前堆的末尾元素，
        反复执行调整+交换，直到构建堆的元素只有一个，算法结束即整个序列有序。

 */
public class HeapSort {
        public static void main(String[] args) {
            int[] array = new int[] {4,6,8,5,9};
            // 接下来就是排序的主体逻辑 从大到小
            sort1(array);
            System.out.println(Arrays.toString(array));
        }

        public static void sort1(int[] array){
            //a 按照完全二叉树的特点 从最后一个非叶子节点 开始
            //按照从下到上 从右到左 的子树的顺序调整 完成大顶锥
            for (int i = array.length/2-1; i >=0 ; i--) {
                heapEngine(array,i,array.length);
            }
            //b c
            for (int i = array.length-1; i >0 ; i--) {
                // 说是交换，其实质就是把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
                swap(array, 0, i);
                // 元素交换之后，最后一个元素无需再考虑排序,排序的，就是已经去掉了部分元素的堆了
                // 而这里，实质上是自上而下，自左向右进行调整的
                heapEngine(array,0,i);
            }
        }

        public static void heapEngine(int[] arr,int begin,int end){
            int temp = arr[begin];

            for (int i = 2*begin+1; i < end; i = 2*i+1) {

                if (i+1<end&&arr[i]<arr[i+1]) i++;

                if (arr[i] > temp) {
                     arr[begin] = arr[i];

                     begin =i;

                }else {
                    break;
                }
            }

            arr[begin] = temp;
        }
        /**
         *
         * @description 本方法只有一个参数，那就是待排序的array
         * @author yuzhao.yang
         * @param
         * @return
         * @time 2018年3月9日 下午2:24:45
         */
        public static void sort(int[] array) {
            // 按照完全二叉树的特点，从最后一个非叶子节点开始，对于整棵树进行大根堆的调整
            // 也就是说，是按照自下而上，每一层都是自右向左来进行调整的
            // 注意，这里元素的索引是从0开始的
            // 另一件需要注意的事情，这里的建堆，是用堆调整的方式来做的
            // 堆调整的逻辑在建堆和后续排序过程中复用的
            for (int i = array.length / 2 - 1; i >= 0; i--) {
                adjustHeap(array, i, array.length);
            }
            System.out.println("建堆结束:"+Arrays.toString(array));
            // 上述逻辑，建堆结束
            // 下面，开始排序逻辑
            for (int j = array.length - 1; j > 0; j--) {
                // 元素交换
                // 说是交换，其实质就是把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
                swap(array, 0, j);
                // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
                // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
                // 而这里，实质上是自上而下，自左向右进行调整的
                adjustHeap(array, 0, j);
            }
        }

        /**
         *
         * @description 这里，是整个堆排序最关键的地方，正是因为把这个方法抽取出来，才更好理解了堆排序的精髓，会尽可能仔细讲解
         * @author yuzhao.yang
         * @param
         * @return
         * @time 2018年3月9日 下午2:54:38
         */
        public static void adjustHeap(int[] array, int i, int length) {
            // 先把当前元素取出来，因为当前元素可能要一直移动
            int temp = array[i];
            // 可以参照sort中的调用逻辑，在堆建成，且完成第一次交换之后，实质上i=0；也就是说，是从根所在的最小子树开始调整的
            // 接下来的讲解，都是按照i的初始值为0来讲述的
            // 这一段很好理解，如果i=0；则k=1；k+1=2
            // 实质上，就是根节点和其左右子节点记性比较，让k指向这个不超过三个节点的子树中最大的值
            // 这里，必须要说下为什么k值是跳跃性的。
            // 首先，举个例子，如果a[0] > a[1]&&a[0]>a[2],说明0,1,2这棵树不需要调整，那么，下一步该到哪个节点了呢？
            // 肯定是a[1]所在的子树了，也就是说，是以本节点的左子节点为根的那棵小的子树
            // 而如果a[0}<a[2]呢，那就调整a[0]和a[2]的位置，继续调整以a[2]为根节点的那棵子树，而且是从左子树开始调整的
            // 所以，这里面的用意就在于，自上而下，自左向右一点点调整整棵树的部分，直到每一颗小子树都满足大根堆的规律为止
            for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
                // 让k先指向子节点中最大的节点
                if (k + 1 < length && array[k] < array[k + 1]) {
                    k++;
                }

                // 如果发现子节点更大，则进行值的交换
                if (array[k] > temp) {
                    array[i] = array[k];
                    // 下面就是非常关键的一步了
                    // 如果子节点更换了，那么，以子节点为根的子树会不会受到影响呢？
                    // 所以，循环对子节点所在的树继续进行判断
                    i = k;
                    // 如果不用交换，那么，就直接终止循环了
                } else {
                    break;
                }
            }
            array[i] = temp;//将temp值放到最终的位置
        }

        /**
         * 交换元素
         *
         * @param arr
         * @param a
         *            元素的下标
         * @param b
         *            元素的下标
         */
        public static void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }

}
