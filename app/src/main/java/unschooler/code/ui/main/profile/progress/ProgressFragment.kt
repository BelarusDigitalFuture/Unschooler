package unschooler.code.ui.main.profile.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_progress.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

import unschooler.code.R
import unschooler.code.ui.main.profile.progress.common.Progress
import unschooler.code.ui.main.profile.progress.common.ProgressAdapter
import unschooler.code.utils.alertOk
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ProgressFragment : DaggerFragment(R.layout.f_progress) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ProgressViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        seeAll.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        list.adapter = ProgressAdapter(
            listOf(
                Progress(
                    "Алгебра",
                    15,
                    40,
                    listOf(R.drawable.user1, R.drawable.user2, R.drawable.user3)
                ),
                Progress(
                    "Геометрия",
                    18,
                    21,
                    listOf(R.drawable.user5, R.drawable.user4, R.drawable.user3)
                ),
                Progress(
                    "Астрономия",
                    4,
                    49,
                    listOf(R.drawable.user3, R.drawable.user5, R.drawable.user1)
                ),
                Progress(
                    "Химия",
                    13,
                    20,
                    listOf(R.drawable.user4, R.drawable.user2, R.drawable.user3)
                )
            )
        )
    }

}
