package unschooler.code.ui.main.profile.another

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_profile_another.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import unschooler.code.NavMainGraphDirections
import unschooler.code.R
import unschooler.code.managers.database.models.Answer
import unschooler.code.ui.main.profile.common.AnswerAdapter
import unschooler.code.utils.alertOk
import unschooler.code.utils.navigateSafe
import java.lang.Exception
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AnotherProfileFragment : DaggerFragment(R.layout.f_profile_another) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<AnotherProfileViewModel> { viewModelFactory }

    val args: AnotherProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answersList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewModel.getUser(args.id) { user ->
            try {
                requireActivity().runOnUiThread {
                    name.text = user.name ?: ""
                    description.text = "Биология"//user?.description ?: ""
                    allRate.text = (user.likes?.all ?: 0).toString() + " /"
                    weekRate.text = "+" + (user.likes?.new ?: 0).toString()

                    if (!user.picture.isNullOrBlank()) {
                        Picasso.get().load(user.picture).error(R.drawable.ic_no_photo).into(photo)
                    } else {
                        photo.setImageResource(R.drawable.ic_no_photo)
                    }
                }
            } catch (e : Exception){
                e.printStackTrace()
            }
        }

        viewModel.getAnswers(args.id).let {
            answersList.adapter = AnswerAdapter(it, false, ::openAnswer) {}
        }

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        answersSeeAll.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        book.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }
    }

    private fun openAnswer(answer: Answer) {
        findNavController().navigateSafe(
            NavMainGraphDirections.openTheme(
                answer.themeId,
                answer.id
            )
        )
    }
}
