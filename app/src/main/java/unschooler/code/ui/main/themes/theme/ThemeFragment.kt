package unschooler.code.ui.main.themes.theme

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_schedule.*
import kotlinx.android.synthetic.main.f_schedule.title
import kotlinx.android.synthetic.main.f_theme.*
import kotlinx.android.synthetic.main.i_answer.view.*
import kotlinx.android.synthetic.main.i_answer_photo.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import unschooler.code.NavMainGraphDirections

import unschooler.code.R
import unschooler.code.ui.main.profile.common.AnswerAdapter
import unschooler.code.ui.main.themes.common.SubjectsAdapter
import unschooler.code.utils.alertOk
import unschooler.code.utils.navigateSafe
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ThemeFragment : DaggerFragment(R.layout.f_theme) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ThemeViewModel> { viewModelFactory }

    val args: ThemeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answersList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewModel.getSubject(args.theme)?.let {
            title.text = it.title
            it.themes.find { it.id == args.theme }?.let { theme ->
                themeTitle.text = theme.title
                themeDescription.text = theme.description
            }
        }

        viewModel.getAnswer(args.theme, args.answer)?.let {
            answer.visibility = VISIBLE

            answerTitle.text = it.title
            answerDescription.text = it.description ?: ""

            userName.text = it.creator.name
            if (!it.creator.picture.isNullOrBlank()) {
                Picasso.get().load(it.creator.picture).error(R.drawable.ic_no_photo).into(userPhoto)
            } else {
                userPhoto.setImageResource(R.drawable.ic_no_photo)
            }


            userName.setOnClickListener { view ->
                openProfile(it.creator.uid)
            }
            userPhoto.setOnClickListener { view ->
                openProfile(it.creator.uid)
            }

            if (it.video.isNullOrBlank()) {
                youtube.visibility = GONE
                image.visibility = VISIBLE
            } else {
                youtube.visibility = VISIBLE
                image.visibility = GONE
                youtube.let { view ->
                    val id =
                        Regex("(?:https?:\\/{2})?(?:w{3}\\.)?youtu(?:be)?\\.(?:com|be)(?:\\/watch\\?v=|\\/)([^\\s&]+)").find(
                            it.video
                                ?: ""
                        )?.groupValues?.getOrNull(1)
                    view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.cueVideo(id ?: "", 0f)
                        }
                    })
                }
            }

        } ?: run {
            answer.visibility = GONE
        }

        val answers = viewModel.getAnswersList(args.theme, args.answer)
        if (answers.isNotEmpty()) {
            moreTitle.visibility = VISIBLE
            answersList.visibility = VISIBLE

            answersList.adapter = AnswerAdapter(
                answers, true, {
                    findNavController().navigateSafe(
                        NavMainGraphDirections.openTheme(
                            it.themeId,
                            it.id
                        )
                    )
                }, ::openProfile
            )
        } else {
            moreTitle.visibility = GONE
            answersList.visibility = GONE
        }

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        like.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        share.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        book.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }
    }

    private fun openProfile(uid: String) {
        if (viewModel.isMe(uid)) {
            findNavController().navigateSafe(NavMainGraphDirections.openProfile())
        } else {
            findNavController().navigateSafe(NavMainGraphDirections.openAnotherProfile(uid))
        }
    }
}
