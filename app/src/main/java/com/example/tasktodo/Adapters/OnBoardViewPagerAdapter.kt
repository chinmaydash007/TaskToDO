package com.example.tasktodo.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.tasktodo.Model.OnBoardScreenComponent
import com.example.tasktodo.R

class OnBoardViewPagerAdapter(
    var screenComponentList: List<OnBoardScreenComponent>,
    var mContext: Context
) : PagerAdapter() {
    override fun getCount(): Int {
        return screenComponentList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = layoutInflater.inflate(R.layout.on_board_screen_view, container, false)
        val imageView =
            view.findViewById<ImageView>(R.id.intro_imageview)
        val title = view.findViewById<TextView>(R.id.intro_title)
        val description = view.findViewById<TextView>(R.id.intro_description)

        imageView.setImageResource(screenComponentList.get(position).image)
        title.setText(screenComponentList.get(position).title)
        description.setText(screenComponentList.get(position).description)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}