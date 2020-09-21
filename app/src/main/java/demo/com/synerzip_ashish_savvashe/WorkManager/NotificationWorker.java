package demo.com.synerzip_ashish_savvashe.WorkManager;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";


    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public ListenableWorker.Result doWork() {
        Data taskData = getInputData();


        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();

        Log.d("TAG","Work request===>"+"Work Finished");
        return Result.success(outputData);

    }
}
