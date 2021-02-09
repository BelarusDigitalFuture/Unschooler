package unschooler.code.ui.main.profile.common

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.f_profile.*
import kotlinx.android.synthetic.main.i_answer.view.*
import kotlinx.android.synthetic.main.i_answer.view.image
import kotlinx.android.synthetic.main.i_answer.view.title
import kotlinx.android.synthetic.main.i_answer.view.youtube
import kotlinx.android.synthetic.main.i_answer_photo.view.*
import unschooler.code.R
import unschooler.code.managers.database.models.Answer

class AnswerAdapter(val list: List<Answer>, val hasPhoto: Boolean, val listener: (Answer) -> Unit) :
    RecyclerView.Adapter<AnswerAdapter.AnswerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            LayoutInflater.from(parent.context).inflate(
                if (hasPhoto) R.layout.i_answer_photo else R.layout.i_answer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.itemView.title.text = list[position].title
        if (list[position].video.isNullOrBlank()){
            holder.itemView.youtube.visibility = GONE
            holder.itemView.image.visibility = VISIBLE
        } else {
            holder.itemView.youtube.visibility = VISIBLE
            holder.itemView.image.visibility = GONE
            holder.itemView.youtube.let { view ->
                val id = Regex("(?:https?:\\/{2})?(?:w{3}\\.)?youtu(?:be)?\\.(?:com|be)(?:\\/watch\\?v=|\\/)([^\\s&]+)").find(list[position].video
                    ?: "")?.groupValues?.getOrNull(1)
                view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(id ?: "", 0f)
                    }
                })
            }
        }

        if (hasPhoto){
            holder.itemView.userName.text = list[position].creator.name
            if (!list[position].creator.picture.isNullOrBlank()) {
                Picasso.get().load(list[position].creator.picture).error(R.drawable.ic_no_photo).into(holder.itemView.userPhoto)
            } else {
                holder.itemView.userPhoto.setImageResource(R.drawable.ic_no_photo)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    inner class AnswerHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                listener.invoke(list[adapterPosition])
            }
        }
    }
}