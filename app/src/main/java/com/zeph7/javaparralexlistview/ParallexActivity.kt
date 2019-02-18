package com.zeph7.javaparralexlistview

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.util.ArrayList

class  ParallexActivity : AppCompatActivity() {

    private var stickyView: TextView? = null
    private var listView: ListView? = null
    private var heroImageView: View? = null
    private var stickyViewSpacer: View? = null
    private val MAX_ROWS = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Initialise list view, hero image, and sticky view */
        listView = findViewById(R.id.listView) as ListView
        heroImageView = findViewById(R.id.heroImageView)
        stickyView = findViewById(R.id.stickyView) as TextView

        /* Inflate list header layout */
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listHeader = inflater.inflate(R.layout.list_header, null)
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder)

        /* Add list view header */
        listView!!.addHeaderView(listHeader)

        /* Handle list View scroll events */
        listView!!.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {

                /* Check if the first item is already reached to top.*/
                if (listView!!.firstVisiblePosition == 0) {
                    val firstChild = listView!!.getChildAt(0)
                    var topY = 0
                    if (firstChild != null) {
                        topY = firstChild.top
                    }

                    val heroTopY = stickyViewSpacer!!.top
                    stickyView!!.y = Math.max(0, heroTopY + topY).toFloat()

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView!!.y = topY * 0.5f
                }
            }
        })


        /* Populate the ListView with sample data */
        val modelList = ArrayList<String>()
        for (i in 0 until MAX_ROWS) {
            modelList.add("List item $i")
        }

        val adapter = ArrayAdapter(this, R.layout.list_row, modelList)
        listView!!.adapter = adapter
    }
}