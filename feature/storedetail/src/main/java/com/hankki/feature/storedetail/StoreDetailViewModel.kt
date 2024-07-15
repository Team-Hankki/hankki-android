import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.feature.storedetail.StoreState
import com.hankki.feature.storedetail.model.MenuItem
import com.hankki.feature.storedetail.model.StoreDetail
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreDetailViewModel : ViewModel() {
    private val _storeState = MutableStateFlow(StoreState())
    val storeState: StateFlow<StoreState> get() = _storeState.asStateFlow()

    init {
        val mockMenus = listOf(
            MenuItem(name = "수육정식", price = 7900),
            MenuItem(name = "제육정식", price = 8900),
            MenuItem(name = "꼬막정식", price = 7900)
        )
        val mockStoreDetail = StoreDetail(
            name = "한끼네 한정식",
            category = "한식",
            isLiked = false,
            heartCount = 299,
            imageUrls = listOf(),
            menus = mockMenus
        )

        setStoreDetail(mockStoreDetail)
        _storeState.value = _storeState.value.copy(
            buttonLabels = persistentListOf(
                "식당이 사라졌어요",
                "더이상 8000원이하인 메뉴가 없어요",
                "부적절한 제보에요"
            )
        )
    }

    private fun setStoreDetail(storeDetail: StoreDetail) {
        _storeState.value = _storeState.value.copy(
            storeDetail = UiState.Success(storeDetail),
            isLiked = storeDetail.isLiked,
            heartCount = storeDetail.heartCount
        )
    }

    fun toggleLike() {
        viewModelScope.launch {
            val currentState = _storeState.value
            val newLikeStatus = !currentState.isLiked
            val newHeartCount =
                if (newLikeStatus) currentState.heartCount + 1 else currentState.heartCount - 1

            _storeState.value = currentState.copy(
                isLiked = newLikeStatus,
                heartCount = newHeartCount
            )
        }
    }

    fun updateSelectedIndex(index: Int) {
        _storeState.value = _storeState.value.copy(selectedIndex = index)
    }
}
