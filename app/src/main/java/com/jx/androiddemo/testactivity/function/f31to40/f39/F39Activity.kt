package com.jx.androiddemo.testactivity.function.f31to40.f39;

import android.annotation.SuppressLint
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.android.synthetic.main.activity_f39.*

class F39Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private var mU37Adapter: F39Adapter? = null
    private val mList: ArrayList<ListBean> = ArrayList()

    companion object {
        val TAG0 = "F39Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f39
    }

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
        mU37Adapter = F39Adapter(mContext, object : F39Adapter.Listener {
            override fun onItemClick(position: Int, bean: ListBean) {
                tv_content.text = bean.content
            }
        })
        //List：元素可重复
        mList.add(
            ListBean(
                "List__ArrayList",
                "基于数组实现，移除和插入，需要数前移后移,底层结构是一个数组，初始长度为10 容量不足时，扩展为原来的1.5倍也就是扩展为15"
            )
        )
        mList.add(
            ListBean(
                "List__LinkedList(Queue)",
                "基于双向链表实现，插入删除快,底层是一个双向链表，好处是不用扩容，坏处是当你要寻找第N个元素时，实践复杂度为O(n),就是遍历N个元素去找到他 而ArrayList的时间复杂度是 O(1)"
            )
        )

        //Set：元素不可重复
        mList.add(
            ListBean(
                "Set__HashSet，LinkedHashSet",
                "hashset基于map实现，map.put(e, PRESENT)==null，前者存储的元素是无序的，后者有序，按插入顺序排序"
            )
        )
        mList.add(
            ListBean(
                "Set__TreeSet",
                "元素有序，但不是插入顺序，是按自然顺序。例如依次插入元素：treeSet.add(2)、treeSet.add(5)、treeSet.add(8)、treeSet.add(3)，遍历结果为2、3、5、8。如果有特殊需求也可以自定义排序规则"
            )
        )
        mList.add(
            ListBean(
                "Set__ArraySet",
                "基于数组实现，主要特点是占用内存小于前三者，这也是google为了移动端珍贵的内存而打造的数据容器；只适用于数据量小的场景，数据量大性能不佳"
            )
        )

        //Map：键值对，key不可重复，value可重复
        mList.add(
            ListBean(
                "Map__HashMap",
                "元素无序；非线程安全；key和value均允许为null,node存储,底层结构是 一个元素为链表的数组 ， 虽然是数组 但是是无序插入数组的。根据哈希值来插入。 当hash相同则需要用到链表结构 ， 把新插入的但 hashcode值相同的 链在之前插入的后面形成链表，当连得太多 就会形成红黑树，新加入的元素形成连头，第一存放在位置上的就成链尾"
            )
        )
        mList.add(
            ListBean(
                "Map__LinkedHashMap",
                "元素有序，插入顺序；非线程安全；key和value均允许为null,底层是一个元素为链表的数组 + 元素之间的形成的双向链表 ， 即是单向链表形成的双向链表。 双向链表维护元素的次序，这样使得元素看起来是以插入的顺序保存的。"
            )
        )
        mList.add(ListBean("Map__ConcurrentHashMap", "看名字就能猜到它与HashMap相比是线程安全的，在多线程环境中使用"))
        mList.add(
            ListBean(
                "Map__Hashtable",
                "与HashMap相比是线程安全的，它也是早期实现的一个比较粗糙的数据结构，现在基本已经被淘汰了，多线程环境可以使用ConcurrentHashMap"
            )
        )

        //Queue：队列，遵循先进先出
        mList.add(ListBean("Queue__LinkedBlockingQueue", "它与LinkedList相比没有实现List接口，不具备List的功能"))
        mList.add(
            ListBean(
                "Queue__SynchronousQueue",
                "它内部没有存放数据的能力，或者说容量为0（无论调用多少次put方法，调用其size方法得到的都是0）；并且它是个特殊的阻塞队列，不同于其他阻塞队列（队列内数据满/空了才会阻塞put/take线程），每个向其put/take数据的线程都会被阻塞，直到能与之配对的take/put线程到来才会释放，原因也正是因为容量为0，所以队列可以一直看作是满的/空的。"
            )
        )
        /**
         * 打个比方：有一间屋子（阻塞队列）卖面包，面包师（put线程）做完一个面包要放到屋子里（向队列put数据），如果屋子里面包满了放不下了，面包师就会在这里等着（put线程被阻塞），直到屋子里有位置了再将面包放进去然后回去（put线程被释放）；顾客（take线程）来到屋子要买面包（从队列take数据），如果屋子里空了没有面包，顾客就会在这里等着（take线程被阻塞），直到屋子里有面包了再买完离开（take线程被释放）。前面描述的是普通阻塞队列，如果是SynchronousQueue，情形会是这样：面包师做完一个面包要放到屋子里，由于屋子里不能存放面包（即队列无法存放数据），所以任何一个面包师无论何时要将面包放进屋子里都会在这里等（被阻塞），等待有顾客来买面包；同样由于屋子里不能存放面包，也就是一直没有面包，所以任何一个顾客无论何时要来买面包也都会在这里等，等待有面包师做完面包送过来。或者可以理解为面包不会被放进屋子里，面包是从面包师手中直接给到顾客手中，屋子只是为面包师和顾客提供等待的地点（即SynchronousQueue只负责阻塞线程，不负责存放数据）而已
         */
        mList.add(ListBean("Queue__ConcurrentLinkedQueue", "线程安全，使用循环CAS实现非阻塞式并发"))

        mU37Adapter?.setData(mList)
        rv_list.adapter = mU37Adapter
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
    }
}