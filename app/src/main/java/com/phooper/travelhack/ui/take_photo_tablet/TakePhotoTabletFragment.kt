package com.phooper.travelhack.ui.take_photo_tablet

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import com.phooper.travelhack.R
import com.phooper.travelhack.presentation.take_photo_tablet.TakePhotoTabletPresenter
import com.phooper.travelhack.presentation.take_photo_tablet.TakePhotoTabletView
import com.phooper.travelhack.ui.global.BaseFragment
import kotlinx.android.synthetic.main.counter_digital_circle.*
import kotlinx.android.synthetic.main.fragment_take_photo_tablet.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class TakePhotoTabletFragment : BaseFragment(), TakePhotoTabletView {

    override val layoutRes = R.layout.fragment_take_photo_tablet

    @InjectPresenter
    lateinit var presenter: TakePhotoTabletPresenter

    @ProvidePresenter
    fun providePresenter(): TakePhotoTabletPresenter =
        TakePhotoTabletPresenter(arguments?.getString(BARCODE))

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        start_btn.setOnClickListener {
            presenter.startBtnOnClicked()
        }
        exit_btn.setOnClickListener {
            presenter.btnExitOnClicked()
        }
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun changeStartBtnResource(resId: Int) =
        start_img.setImageResource(resId)

    override fun disableStartBtn() {
        start_btn.isClickable = false
    }

    override fun changeStartBtnDigit(digit: String) {
        seconds_left.text = digit
    }

    override fun hideStartBtnDigits() {
        seconds_left.visibility = View.GONE
    }

    override fun showStartBtnDigits() {
        seconds_left.visibility = View.VISIBLE
    }

    override fun hideHintAndExit() {
        TransitionManager.beginDelayedTransition(photo_layout,
            AutoTransition().apply { duration = 250 })
        ConstraintSet().apply {
            clone(photo_layout)
            clear(hint_layout.id, ConstraintSet.START)
            connect(hint_layout.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.START)
            clear(exit_btn.id, ConstraintSet.BOTTOM)
            connect(exit_btn.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

        }.applyTo(photo_layout)
    }

    companion object {
        private const val BARCODE = "barcode"
        fun create(barcode: String) =
            TakePhotoTabletFragment().apply {
                arguments = Bundle().apply {
                    putString(BARCODE, barcode)
                }
            }
    }
}