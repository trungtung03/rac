package com.example.rac

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private val arrOption = arrayListOf(1, 2, 3)
    private var count: Int = 0
    private var numberOption: Int = 0
    private var numberResult: Int = 0
    var shopAdapter: ShopAdapter? = null
    private var shopArrayList = arrayListOf<Shop>()

    companion object {
        var dialog: Dialog? = null
        var score: Int = 100
        var numberOfTurns: Int = 10
        @SuppressLint("StaticFieldLeak")
        var tvNumberOfTurn: TextView? = null

        fun closeShop() {
            dialog!!.dismiss()
        }

        @SuppressLint("SetTextI18n")
        fun plusScore(soLuot: Int) {
            val strResult = (numberOfTurns + soLuot).toString()
            tvNumberOfTurn!!.text = strResult
        }

        fun resetScore(soLuot: Int) {
            if(numberOfTurns <= 0) {
                val giaTri = tvNumberOfTurn!!.text.toString().toInt()
                numberOfTurns = giaTri + soLuot
                tvNumberOfTurn!!.text = numberOfTurns.toString()
            }
        }
    }

    private var countDownTimer: CountDownTimer
        get() {
            TODO()
        }
        set(value) {}

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        tvNumberOfTurn = findViewById(R.id.tv_number_of_turn)

        tv_number_of_turn.text = numberOfTurns.toString()
        tv_score.text = "Score: $score"

        hammer_selection.setOnClickListener {
            if (score >= 100) {
                if (numberOfTurns in 1..100000) {
                    numberOption = 2
                    hammer_selection.setBackgroundColor(Color.parseColor("#89FF00"))
                    bag_choice.isClickable = false
                    scissors_selection.isClickable = false
                    hammer_selection.isClickable = false
                    calculatePoints(context = this)
                    if (score <= 0) {
                        score = 0
                        tv_score.text = "Score: $score"
                        Toast.makeText(
                            this,
                            "Bạn đã hết điểm vui lòng bấm chơi lại",
                            Toast.LENGTH_SHORT
                        ).show()
                        img_refresh.visibility = View.VISIBLE
                    }
                } else if (numberOfTurns <= 0) {
                    Toast.makeText(
                        this,
                        "Bạn đã hết lượt chơi vui lòng bấm chơi lại",
                        Toast.LENGTH_SHORT
                    ).show()
                    img_refresh.visibility = View.VISIBLE
                }
            } else if (score <= 0) {
                score = 0
                tv_score.text = "Score: $score"
                Toast.makeText(this, "Bạn đã hết điểm vui lòng bấm chơi lại", Toast.LENGTH_SHORT)
                    .show()
                img_refresh.visibility = View.VISIBLE
            }
        }
        bag_choice.setOnClickListener {
            if (score >= 100) {
                if (numberOfTurns in 1..100000) {
                    numberOption = 3
                    bag_choice.setBackgroundColor(Color.parseColor("#89FF00"))
                    hammer_selection.isClickable = false
                    scissors_selection.isClickable = false
                    bag_choice.isClickable = false
                    calculatePoints(context = this)
                    if (score <= 0) {
                        score = 0
                        tv_score.text = "Score: $score"
                        Toast.makeText(
                            this,
                            "Bạn đã hết điểm vui lòng bấm chơi lại",
                            Toast.LENGTH_SHORT
                        ).show()
                        img_refresh.visibility = View.VISIBLE
                    }
                } else if (numberOfTurns <= 0) {
                    Toast.makeText(
                        this,
                        "Bạn đã hết lượt chơi vui lòng bấm chơi lại",
                        Toast.LENGTH_SHORT
                    ).show()
                    img_refresh.visibility = View.VISIBLE
                }
            } else if (score <= 0) {
                score = 0
                tv_score.text = "Score: $score"
                Toast.makeText(
                    this,
                    "Bạn đã hết điểm vui lòng bấm chơi lại",
                    Toast.LENGTH_SHORT
                ).show()
                img_refresh.visibility = View.VISIBLE
            }
        }
        scissors_selection.setOnClickListener {
            if (score >= 100) {
                if (numberOfTurns in 1..100000) {
                    numberOption = 1
                    scissors_selection.setBackgroundColor(Color.parseColor("#89FF00"))
                    hammer_selection.isClickable = false
                    bag_choice.isClickable = false
                    scissors_selection.isClickable = false
                    calculatePoints(context = this)
                    if (score <= 0) {
                        score = 0
                        tv_score.text = "Score: $score"
                        Toast.makeText(
                            this,
                            "Bạn đã hết điểm vui lòng bấm chơi lại",
                            Toast.LENGTH_SHORT
                        ).show()
                        img_refresh.visibility = View.VISIBLE
                    }
                } else if (numberOfTurns <= 0) {
                    Toast.makeText(
                        this,
                        "Bạn đã hết lượt chơi số điểm bạn đạt được là: $score",
                        Toast.LENGTH_SHORT
                    ).show()
                    img_refresh.visibility = View.VISIBLE
                }
            } else if (score <= 0) {
                score = 0
                tv_score.text = "Score: $score"
                Toast.makeText(this, "Bạn đã hết điểm vui lòng bấm chơi lại", Toast.LENGTH_SHORT)
                    .show()
                img_refresh.visibility = View.VISIBLE
            }
        }

        img_refresh.setOnClickListener {
            numberOfTurns = 2
            tv_number_of_turn.text = numberOfTurns.toString()
            score = 100
            tv_score.text = "Score: $score"
        }

        img_shop.setOnClickListener {
            clickShop()
        }
        showShop()
    }

    private fun clickShop() {
        dialog!!.show()
    }

    private fun showShop() {
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.dialog)
        dialog!!.setCanceledOnTouchOutside(false)
//        shopArrayList = ArrayList<Shop>()
        val rcv: RecyclerView = dialog!!.findViewById(R.id.rcv_shop_dialog)
        shopAdapter = ShopAdapter(this@MainActivity, shopArrayList)
        addList()
        Log.d("ddd", shopArrayList.size.toString() + "")
        LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        ).also { rcv.layoutManager = it }
        rcv.layoutManager = GridLayoutManager(this, 2)
        rcv.adapter = shopAdapter
        dialog!!.exit_dialog.setOnClickListener { dialog!!.dismiss() }
    }

    private fun addList() {
        shopArrayList.add(Shop(5, 25000))
        shopArrayList.add(Shop(10, 50000))
        shopArrayList.add(Shop(15, 72000))
        shopArrayList.add(Shop(20, 90000))
        shopArrayList.add(Shop(25, 114999))
        shopArrayList.add(Shop(30, 133000))
        shopArrayList.add(Shop(35, 145000))
        shopArrayList.add(Shop(40, 164500))
        shopArrayList.add(Shop(45, 187000))
        shopArrayList.add(Shop(50, 202213))
        shopArrayList.add(Shop(55, 221833))
        shopArrayList.add(Shop(60, 243578))
        shopArrayList.add(Shop(65, 267321))
        shopArrayList.add(Shop(70, 298333))
        shopArrayList.add(Shop(75, 320485))
        shopArrayList.add(Shop(80, 345232))
        shopArrayList.add(Shop(85, 372183))
        shopArrayList.add(Shop(90, 409421))
    }

    fun randomXucSac(context: Context) {
        countDownTimer = object : CountDownTimer(2000, 60) {
            override fun onTick(millisUntilFinished: Long) {
                thietLapRandom()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                hammer_selection.setBackgroundColor(Color.parseColor("#FF3D00"))
                bag_choice.setBackgroundColor(Color.parseColor("#FF3D00"))
                scissors_selection.setBackgroundColor(Color.parseColor("#FF3D00"))
                if (numberResult > numberOption) {
                    Toast.makeText(context, "Bạn đã thua và bị trừ -15 điểm", Toast.LENGTH_SHORT)
                        .show()
                    score -= 15
                    tv_score.text = "Score: $score"
                } else if (numberResult < numberOption) {
                    score += 20
                    tv_score.text = "Score: $score"
                    Toast.makeText(
                        context,
                        "Chúc mừng bạn đã thắng và được +20 điểm",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Hòa", Toast.LENGTH_SHORT).show()
                }
                numberOption = 0
                hammer_selection.isClickable = true
                scissors_selection.isClickable = true
                bag_choice.isClickable = true
                if (score <= 0) {
                    score = 0
                    tv_score.text = "Score: $score"
                    Toast.makeText(
                        context,
                        "Bạn đã hết điểm vui lòng bấm chơi lại",
                        Toast.LENGTH_SHORT
                    ).show()
                    img_refresh.visibility = View.VISIBLE
                }
            }
        }.start()
    }

    fun thietLapRandom() {
        count = Random.nextInt(arrOption.size)
        numberResult = (count + 1)
        val str = resources.getIdentifier("keo_" + arrOption[count], "drawable", packageName)
        img_result.setImageResource(str)
    }

    fun calculatePoints(context: Context) {
        if (numberOfTurns in 1..100000) {
            val giaTri = tv_number_of_turn.text.toString().toInt()
            numberOfTurns = (giaTri - 1)
            tv_number_of_turn.text = numberOfTurns.toString()
            randomXucSac(this)
        } else if (numberOfTurns <= 0) {
            img_refresh.visibility = View.VISIBLE
            Toast.makeText(
                context,
                "Bạn đã hết lượt chơi vui lòng bấm chơi lại",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}