package dehnavi.sajjad.foodapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dehnavi.sajjad.foodapp.R
import dehnavi.sajjad.foodapp.data.model.ResponseCategoryList
import dehnavi.sajjad.foodapp.databinding.ItemCategoriesBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor() : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    //binding
    private lateinit var binding: ItemCategoriesBinding
    private var categoryList = emptyList<ResponseCategoryList.Category>()
    private var selectedItem = -1

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(item: ResponseCategoryList.Category) {
            binding.apply {
                itemCategoriesImg.load(item.strCategoryThumb) {
                    crossfade(true)
                    crossfade(500)
                }
                itemCategoriesTxt.text = item.strCategory
                //Click
                root.setOnClickListener {
                    selectedItem = adapterPosition
                    notifyDataSetChanged()
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                //Change color
                if (selectedItem == adapterPosition) {
                    root.setBackgroundResource(R.drawable.bg_rounded_selcted)
                } else {
                    root.setBackgroundResource(R.drawable.bg_rounded_white)
                }
            }
        }


    }
    private var onItemClickListener: ((ResponseCategoryList.Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseCategoryList.Category) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        binding = ItemCategoriesBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.onBind(categoryList[p1])
        p0.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = categoryList.size


    fun setData(data: List<ResponseCategoryList.Category>) {
        val moviesDiffUtil = MoviesDiffUtils(categoryList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        categoryList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<ResponseCategoryList.Category>,
        private val newItem: List<ResponseCategoryList.Category>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}