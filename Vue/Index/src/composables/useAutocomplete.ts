import { ref, onMounted } from 'vue'

interface RestaurantItem {
  value: string
}

export function useAutocomplete() {
  const type = ref('');

  const restaurants = ref<RestaurantItem[]>([])
  const querySearch = (queryString: string, cb: (results: RestaurantItem[]) => void) => {
    const results = queryString
      ? restaurants.value.filter(createFilter(queryString))
      : restaurants.value
    cb(results)
  }
  const createFilter = (queryString: string) => {
    return (restaurant: RestaurantItem) => {
      return (
        restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      )
    }
  }
  const loadAll = () => {
    return [
      { value: 'Q&A' },
      { value: '技术' },
      { value: '情感' },
      { value: '灌水' },
      { value: '喵喵喵' },
    ]
  }

  const handleSelect = (item: RestaurantItem) => {
    console.log(item.value)
  }

  onMounted(() => {
    restaurants.value = loadAll()
  })

  return {
    type,
    querySearch,
    handleSelect,
  }
}
