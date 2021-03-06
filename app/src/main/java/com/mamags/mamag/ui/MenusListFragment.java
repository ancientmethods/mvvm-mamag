package com.mamags.mamag.ui;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.R;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.viewmodel.IView;
import com.mamags.mamag.viewmodel.MenuView;
import com.mamags.mamag.viewmodel.MenuViewModel;

import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     MenusListFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link MenusListFragment.Listener}.</p>
 */

public class MenusListFragment extends BaseFragment<ViewDataBinding,MenuViewModel> implements MenuView {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    RecyclerView recyclerView;

    // TODO: Customize parameters
    public static MenusListFragment newInstance(int itemCount) {
        final MenusListFragment fragment = new MenusListFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestAPI restAPI = (RestAPI)this;
        //attach the view model interface object
        viewModel = new MenuViewModel(ctx.getApplication(),restAPI );
        viewModel.attach(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       bindView(R.layout.fragment_menu_list);

        return binding.getRoot();

        //return inflater.inflate(R.layout.fragment_menu_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below
        viewModel.getMenuList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void loadMenuResults(List<Menu> menuList) {
        //recyclerView.setAdapter(new MenuAdapter(14));
        recyclerView.setAdapter(new MenuAdapter(menuList));
    }

    public interface Listener {
        void onMenuClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_menu_list_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);

            text.setOnClickListener(v ->{});
        }

    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Menu> menuList;

        MenuAdapter(List<Menu> menuList) {
            this.menuList = menuList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(menuList.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

    }


}
