import com_header from '@/components/com_header.vue'
import com_footer from '@/components/com_footer.vue'
import com_account_nav from '@/components/com_account_nav'

import search_window from '@/components/header/search_window'
import img_logo from '@/components/header/img_logo'
import com_cartegory from '@/components/header/com_cartegory'
import com_tools from '@/components/header/com_tools'

import com_display from '@/components/home/com_display'
import com_img from '@/components/com_img'
import category_name from '@/components/home/category_name'
import category_items from '@/components/home/category_items'
import com_item from '@/components/home/com_item'

import com_login from '@/components/login/com_login'
import com_join from '@/components/join/com_join'

import group_checkbox from '@/components/search/group_checkbox'
import com_items from '@/components/search/com_items'
import com_sort from '@/components/search/com_sort'

import com_item_info from '@/components/item/com_item_info'
import com_recommend from '@/components/item/com_recommend'
import com_desc from '@/components/item/com_desc'
import com_review from '@/components/item/com_review'
import com_query from '@/components/item/com_query'
import com_exchange from '@/components/item/com_exchange'
import com_review_unit from '@/components/item/com_review_unit'
import com_query_unit from '@/components/item/com_query_unit'
import com_review_posting from '@/components/item/com_review_posting'

import com_bucket from '@/components/bucket/com_bucket'

import com_account_header from '@/components/account/com_account_header'
import com_orders from '@/components/account/com_orders'
import com_orders_unit from '@/components/account/com_orders_unit'

import com_history_query from '@/components/historyQuery/com_history_query'
import com_input_comment from '@/components/historyQuery/com_input_comment'
import com_make_query from '@/components/historyQuery/com_make_query'

import com_query_chat from '@/components/historyQuery/com_query_chat'
import com_query_chat_unit from '@/components/historyQuery/com_query_chat_unit'
import com_query_state from '@/components/historyQuery/com_query_state.vue'

import com_updateAccount from '@/components/updateAccount/com_updateAccount.vue'

import com_item_info_create from '@/components/createItem/com_item_info_create'
import com_desc_create from '@/components/createItem/com_desc_create'
import com_img_create from '@/components/createItem/com_img_create'
import com_tag_create from '@/components/createItem/com_tag_create'

import com_history_review from '@/components/historyReview/com_history_review'
import com_review_header_unit from '@/components/historyReview/com_review_header_unit'

const components = {
    "com_header": com_header,
    "com_footer": com_footer,

    "search_window": search_window,
    "img_logo": img_logo,
    "com_cartegory": com_cartegory,
    "com_tools": com_tools,
    
    "com_display": com_display,
    "com_img": com_img,
    "category_name": category_name,
    "category_items": category_items,
    "com_item": com_item,

    "com_login": com_login,
    "com_join": com_join,

    "group_checkbox": group_checkbox,
    "com_items": com_items,
    "com_sort": com_sort,

    "com_item_info": com_item_info,
    "com_recommend": com_recommend,
    "com_desc": com_desc,
    "com_review": com_review,
    "com_query": com_query,
    "com_exchange": com_exchange,
    "com_review_unit": com_review_unit,
    "com_query_unit": com_query_unit,
    "com_review_posting": com_review_posting,

    "com_bucket": com_bucket,

    "com_account_header": com_account_header,
    "com_account_nav": com_account_nav,
    "com_orders": com_orders,
    "com_orders_unit": com_orders_unit,

    "com_history_query": com_history_query,
    "com_input_comment": com_input_comment,
    "com_make_query": com_make_query,

    "com_query_chat": com_query_chat,
    "com_query_chat_unit": com_query_chat_unit,
    "com_query_state": com_query_state,

    "com_updateAccount": com_updateAccount,

    "com_item_info_create": com_item_info_create,
    "com_desc_create": com_desc_create,
    "com_img_create": com_img_create,
    "com_tag_create": com_tag_create,

    "com_history_review": com_history_review,
    "com_review_header_unit": com_review_header_unit,
};

export default {
    install(Vue) {
        for(const com in components) {
            Vue.component(`${com}`, components[com]);
        }
    }
};
