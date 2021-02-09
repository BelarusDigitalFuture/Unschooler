package unschooler.code.ui.main.profile

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_profile.*
import kotlinx.android.synthetic.main.f_schedule.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import unschooler.code.NavMainGraphDirections
import unschooler.code.R
import unschooler.code.managers.database.models.Answer
import unschooler.code.ui.main.profile.common.AnswerAdapter
import unschooler.code.utils.alertOk
import unschooler.code.utils.navigateSafe
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ProfileFragment : DaggerFragment(R.layout.f_profile) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ProfileViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answersList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        likedAnswersList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recommendationsList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            name.text = user?.name ?: ""
            description.text = "Биология"//user?.description ?: ""
            allRate.text = (user?.likes?.all ?: 0).toString() + " /"
            weekRate.text = "+" + (user?.likes?.new ?: 0).toString()

            allCoins.text = (user?.coins?.all ?: 0).toString()

            if (!user?.picture.isNullOrBlank()) {
                Picasso.get().load(user?.picture).error(R.drawable.ic_no_photo).into(photo)
            } else {
                photo.setImageResource(R.drawable.ic_no_photo)
            }
        })

        more.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), more)
            popupMenu.inflate(R.menu.profile_menu)

            popupMenu
                .setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.exit -> {
                            AuthUI.getInstance().signOut(requireContext())
                            true
                        }
                        else -> false
                    }
                }
            popupMenu.show()
        }

        getCoins.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        answersSeeAll.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        likedAnswersSeeAll.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        viewModel.myAnswers.observe(viewLifecycleOwner, Observer {
            answersList.adapter = AnswerAdapter(it, false, ::openAnswer)
        })

        viewModel.likedAnswers.observe(viewLifecycleOwner, Observer {
            likedAnswersList.adapter = AnswerAdapter(it, true, ::openAnswer)
        })

        viewModel.answers.observe(viewLifecycleOwner, Observer {
            recommendationsList.adapter = AnswerAdapter(it, true, ::openAnswer)
        })
    }

    private fun openAnswer(answer: Answer) {
        findNavController().navigateSafe(NavMainGraphDirections.openTheme(answer.themeId, answer.id))
    }
}
