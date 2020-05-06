package com.example.tasktodo.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.tasktodo.Adapters.OnBoardViewPagerAdapter
import com.example.tasktodo.Model.OnBoardScreenComponent
import com.example.tasktodo.R
import com.example.tasktodo.Utils.ZoomOutPageTransformer
import me.relex.circleindicator.CircleIndicator

class OnBoardScreen : AppCompatActivity() {
    lateinit var onBroadScreenPagerAdapter: OnBoardViewPagerAdapter
    lateinit var list: ArrayList<OnBoardScreenComponent>
    lateinit var viewPager: ViewPager
    lateinit var skip: TextView
    lateinit var next: TextView
    lateinit var circleIndicator3: CircleIndicator
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board_screen)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        viewPager = findViewById(R.id.viewpager)
        skip = findViewById(R.id.textView2)
        next = findViewById(R.id.textView)
        circleIndicator3 = findViewById(R.id.circleindicator)

        list = ArrayList()
        list.add(
            OnBoardScreenComponent(
                " Add Todo's",
                "You can add your todo so that you can keep track of your dialy task",
                R.drawable.one
            )
        )
        list.add(
            OnBoardScreenComponent(
                "Mark when done",
                "Check Mark when done with the task",
                R.drawable.two
            )
        )
        list.add(
            OnBoardScreenComponent(
                "Be More Productive",
                "Get productive with the app",
                R.drawable.three
            )
        )

        onBroadScreenPagerAdapter = OnBoardViewPagerAdapter(list, this)
        viewPager.adapter = onBroadScreenPagerAdapter
        circleIndicator3.setViewPager(viewPager)
        onBroadScreenPagerAdapter.registerDataSetObserver(circleIndicator3.dataSetObserver)
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())


        next.setOnClickListener {
            position = viewPager.currentItem
            if (next.text == "Finish" && position == list.size - 1) {
                goToMainActivity()
            }
            if (position < list.size - 1) {
                position++
                viewPager.currentItem = position
            }
            if (position == list.size - 1) {
                next.text = "Finish"
            }
        }
        skip.setOnClickListener { goToMainActivity() }

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position < list.size - 1) {
                    next.text = "Next"
                }
                if (position == list.size - 1) {
                    next.text = "Finish"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun goToMainActivity() {
        val intent = Intent(this@OnBoardScreen, LoginScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
