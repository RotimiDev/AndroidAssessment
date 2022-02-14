package com.example.androidassessment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.androidassessment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isColorPaletteVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        colorSelector()
        setUpViewElement()

    }

    private fun toggleColorPaletteImageView() {
        isColorPaletteVisible = !isColorPaletteVisible
        if (isColorPaletteVisible){
            binding.colorSet.visibility = View.VISIBLE
        } else {
            binding.colorSet.visibility = View.GONE
        }
    }

    private fun setUpViewElement() {

        binding.colorPaletteLayout.setOnClickListener {
            canvasState = COLOR
            toggleOnSpecificLayout(it, binding.colorPalette)
            toggleColorPaletteImageView()
        }

        binding.pencilLayout.setOnClickListener {
            canvasState = PENCIL
            binding.colorSet.visibility = View.GONE
            isColorPaletteVisible = false
            toggleOnSpecificLayout(it, binding.pencil)
        }

        binding.arrowLayout.setOnClickListener {
            canvasState = ARROW
            binding.colorSet.visibility = View.GONE
            isColorPaletteVisible = false
            toggleOnSpecificLayout(it, binding.arrow)
        }

    }

    // Set colours
    private fun colorSelector() {
        binding.apply {
            blackColor.setOnClickListener {
                val color = ResourcesCompat.getColor(resources, R.color.black, null)
                mCanvas.myCanvasView.setColor(color)
            }
            redColor.setOnClickListener {
                val color = ResourcesCompat.getColor(resources, R.color.red, null)
                mCanvas.myCanvasView.setColor(color)
            }
            greenColor.setOnClickListener {
                val color = ResourcesCompat.getColor(resources, R.color.green, null)
                mCanvas.myCanvasView.setColor(color)
            }
            blueColor.setOnClickListener {
                val color = ResourcesCompat.getColor(resources, R.color.blue, null)
                mCanvas.myCanvasView.setColor(color)
            }
        }
    }

    private fun toggleOffAllLayouts() {

        binding.apply {
            setViewBackgroundColor(pencilLayout, R.color.light_ash_color)
            setViewBackgroundColor(arrowLayout, R.color.light_ash_color)
            setViewBackgroundColor(squareLayout, R.color.light_ash_color)
            setViewBackgroundColor(circleLayout, R.color.light_ash_color)
            setViewBackgroundColor(colorPaletteLayout, R.color.light_ash_color)
            pencil.setColorFilter(
                ContextCompat.getColor(
                this@MainActivity, R.color.ic_color))
            arrow.setColorFilter(ContextCompat.getColor(
                this@MainActivity, R.color.ic_color))
            square.setColorFilter(ContextCompat.getColor(
                this@MainActivity, R.color.ic_color))
            circle.setColorFilter(ContextCompat.getColor(
                this@MainActivity, R.color.ic_color))
            colorPalette.setColorFilter(ContextCompat.getColor(
                this@MainActivity, R.color.ic_color))
        }
    }

    private fun setViewBackgroundColor(view: View, backgroundColor: Int) {
        val drawable: GradientDrawable = view.background as GradientDrawable
        drawable.setColor( ContextCompat.getColor(this@MainActivity,
            backgroundColor))
    }

    private fun toggleOnSpecificLayout(backgroundView: View, imageView: ImageView) {
        toggleViewBackground(object : ViewCallback {
            override fun toggleBackground() {
                toggleOffAllLayouts()
            }
        })
        imageView.setColorFilter(ContextCompat.getColor(
            this@MainActivity, R.color.black))
        setViewBackgroundColor(backgroundView, R.color.dark_ash)
    }


    private fun toggleViewBackground(viewCallback: ViewCallback) {
        viewCallback.toggleBackground()
    }
}

interface ViewCallback {
    fun toggleBackground()
}

