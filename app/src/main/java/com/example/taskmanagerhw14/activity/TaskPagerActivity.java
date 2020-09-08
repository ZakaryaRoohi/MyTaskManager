package com.example.taskmanagerhw14.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerhw14.R;
import com.example.taskmanagerhw14.Repository.TasksRepository;
import com.example.taskmanagerhw14.Repository.UserRepository;
import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.fragment.TaskDetailFragment;
import com.example.taskmanagerhw14.fragment.TasksFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class TaskPagerActivity extends AppCompatActivity implements TaskDetailFragment.Callbacks {

    public static final String EXTRA_BUNDLE_USERNAME = "com.example.taskmanagerhw13.activity.extraBundleUsername";
    public static final String TASK_DETAIL_FRAGMENT_DIALOG_TAG = "TaskDetailFragmentDialogTag";
    public static final int TASK_DETAIL_REQUEST_CODE = 101;
    public static final String BUNDLE_USERNAME = "com.example.taskmanagerhw13.activity.bundleUsername";
    private TasksRepository mTasksRepository;
    private ViewPager2 viewPager;
    private String mUsername;
    private UserRepository mUserRepository;
    private TasksFragment mTasksFragmentDone;
    private TasksFragment mTasksFragmentDoing;
    private TasksFragment mTasksFragmentTodo;

    String[] titles = {"Done", "Doing", "Todo"};
    private FragmentStateAdapter pagerAdapter;

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_BUNDLE_USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        if (savedInstanceState != null) {
            mUsername = savedInstanceState.getString(BUNDLE_USERNAME);

        }else {
            Intent intent = getIntent();
            mUsername = intent.getStringExtra(EXTRA_BUNDLE_USERNAME);

        }
        this.setTitle(mUsername);
        mTasksFragmentDone = TasksFragment.newInstance(TaskState.DONE, mUsername);
        mTasksFragmentDoing = TasksFragment.newInstance(TaskState.DOING, mUsername);
        mTasksFragmentTodo = TasksFragment.newInstance(TaskState.TODO, mUsername);

        mUserRepository = UserRepository.getInstance();
        mTasksRepository = TasksRepository.getInstance();
        findViews();
        setListeners();

        pagerAdapter = new TaskPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout_task_state);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])
        ).attach();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_USERNAME, mUsername);
    }

    private void findViews() {
        viewPager = findViewById(R.id.view_pager_fragments);

    }

    private void setListeners() {

    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
//            TasksRepository.getInstance().cleanTaskRepository();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

//    @Override
//    public void onAddTaskClicked() {
//        Task newTask = new Task(mUsername);
//        mTasksRepository = TasksRepository.getInstance();
//        mTasksRepository.addTask(newTask);
//
//        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(newTask.getId());
//        taskDetailFragment.show(getSupportFragmentManager(), TASK_DETAIL_FRAGMENT_DIALOG_TAG);
//    }

    @Override
    public void updateTasksFragment(TaskState taskState, String username) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container,TasksFragment.newInstance(taskState,username)).commit();

//        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if (tasksFragment != null) {
//            tasksFragment.updateUI();
//        }
//        else{
//            TasksFragment tasksFragment1 = TasksFragment.newInstance(taskState,username);
//            tasksFragment1.updateUI();
//        }
//        viewPager.setAdapter(pagerAdapter);
        mTasksRepository = TasksRepository.getInstance();
        switch (taskState) {
            case DONE:
                mTasksFragmentDone.updateUI();
                break;
            case DOING:
                mTasksFragmentDoing.updateUI();
                break;
            case TODO:
                mTasksFragmentTodo.updateUI();
                break;
        }

//        TasksFragment tasksFragment = (TasksFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        tasksFragment.update();
    }

//    @Override
//    public void onTaskUpdated() {
//        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_container);
//        tasksFragment.updateUI();
//
//    }

//    @Override
//    public void onTaskUpdated() {
//
//            TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.fragment_container);
//
//            tasksFragment.updateUI();
//
//    }

    private class TaskPagerAdapter extends FragmentStateAdapter {
        public TaskPagerAdapter(FragmentActivity fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getItemCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
//                    return TasksFragment.newInstance(TaskState.DONE, mUsername);
                    return mTasksFragmentDone;
                case 1:
//                    return TasksFragment.newInstance(TaskState.DOING, mUsername);
                    return mTasksFragmentDoing;
                case 2:
                    return mTasksFragmentTodo;
            }
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mUserRepository.getUserType(mUsername) != null) {
            switch (mUserRepository.getUserType(mUsername)) {
                case USER:
                    MenuInflater inflater1 = getMenuInflater();
                    inflater1.inflate(R.menu.menu_user_task_pager, menu);
                    return true;
                case ADMIN:
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.menu_admin_task_pager, menu);
                    return true;
            }

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                Toast.makeText(this, R.string.add_soon, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_clear_tasks:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_question);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        mTasksRepository.clearTaskRepository();
//                        viewPager.setAdapter(pagerAdapter);
                        switch (mUserRepository.getUserType(mUsername)) {
                            case USER:
                                mTasksRepository.deleteUserTask(mUsername);
                                viewPager.setAdapter(pagerAdapter);
                                break;
                            case ADMIN:
                                mTasksRepository.clearTaskRepository();
                                viewPager.setAdapter(pagerAdapter);
                                break;
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.menu_item_log_out:
                    this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mTasksFragmentDone = TasksFragment.newInstance(TaskState.DONE, mUsername);
//        mTasksFragmentDoing = TasksFragment.newInstance(TaskState.DOING, mUsername);
//        mTasksFragmentTodo = TasksFragment.newInstance(TaskState.TODO, mUsername);
//        mTasksFragmentDone.updateUI();
//        mTasksFragmentDoing.updateUI();
//        mTasksFragmentTodo.updateUI();
    }
}