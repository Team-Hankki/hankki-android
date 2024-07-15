import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.feature.storedetail.model.MenuItem
import com.hankki.feature.storedetail.model.StoreDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreDetailViewModel : ViewModel() {
    private val _storeDetail = MutableStateFlow<UiState<StoreDetail>>(UiState.Loading)
    val storeDetail: StateFlow<UiState<StoreDetail>> get() = _storeDetail.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> get() = _isLiked.asStateFlow()

    private val _heartCount = MutableStateFlow(0)
    val heartCount: StateFlow<Int> get() = _heartCount.asStateFlow()

    private val _selectedIndex = MutableStateFlow(-1)
    val selectedIndex: StateFlow<Int> get() = _selectedIndex.asStateFlow()

    private val _buttonLabels = MutableStateFlow(listOf<String>())
    val buttonLabels: StateFlow<List<String>> get() = _buttonLabels.asStateFlow()

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
        _buttonLabels.value = listOf(
            "식당이 사라졌어요",
            "더이상 8000원이하인 메뉴가 없어요",
            "부적절한 제보에요"
        )
    }

    private fun setStoreDetail(storeDetail: StoreDetail) {
        _storeDetail.value = UiState.Success(storeDetail)
        _isLiked.value = storeDetail.isLiked
        _heartCount.value = storeDetail.heartCount
    }

    fun toggleLike() {
        viewModelScope.launch {
            _isLiked.value = !_isLiked.value
            _heartCount.value = if (_isLiked.value) _heartCount.value + 1 else _heartCount.value - 1
        }
    }

    fun updateSelectedIndex(index: Int) {
        _selectedIndex.value = index
    }
}
