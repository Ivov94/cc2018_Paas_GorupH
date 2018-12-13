using Android;
using Android.App;
using Android.Content;
using Android.Content.Res;
using Android.Graphics;

using Android.OS;
using Android.Provider;
using Android.Support.V4.App;
using Android.Support.V4.Content;
using Android.Support.V7.App;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Bumptech.Glide;
using Paas.GroupH.Model;
using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using AndroidFile = Java.IO.File;

namespace Paas.GroupH.Fragments
{


    [Activity()]
    public class RestActivity : AppCompatActivity
    {

        private ListView listView;
        private Button backButton;
        private ImageView newImage;
        protected async override void OnCreate(Bundle savedInstanceState)
        {
            
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.main2);

            Helper.Values.CreateDir();

            string filename = Intent.GetStringExtra("fileName");

            Log.Info(Helper.Values.LogTag, " imagename..." + filename);

            string result = Intent.GetStringExtra("result");

            backButton = FindViewById<Button>(Resource.Id.backTo);
            backButton.Click += delegate
            {
                BackTo();
            };
            listView = FindViewById<ListView>(Resource.Id.listView);
            listView.Adapter = new ArrayAdapter<string>(this, Resource.Layout.listItem, Resource.Id.label);
            newImage = FindViewById<ImageView>(Resource.Id.newImage);

            this.AddItem(result);

            if (!String.IsNullOrEmpty(filename))
            {

                int maxTryCount = 100;
                int numberOfAttempts = 1;
                do
                {
                    try
                    {
                        var processing = await RestService.GetProgressingData(Helper.Values.DefaultProgressPath, filename);

                        this.AddItem(string.Format("try {0}: filename {1} red {2} blue {3} green {4} negative {5} image-join {6}", numberOfAttempts, processing.ImageName, processing.FilterRedDone, processing.FilterBlueDone, processing.FilterGreenDone, processing.FilterNegativeDone, processing.ImageJoinDone));
                        Log.Info(Helper.Values.LogTag, string.Format("AddItem: try {0}: filename {1} red {2} blue {3} green {4} negative {5} image-join {6}", numberOfAttempts, processing.ImageName, processing.FilterRedDone, processing.FilterBlueDone, processing.FilterGreenDone, processing.FilterNegativeDone, processing.ImageJoinDone));

                        if (processing.FilterBlueDone && processing.FilterGreenDone && processing.FilterNegativeDone && processing.FilterRedDone && processing.ImageJoinDone)
                        {
                            break;
                        }
                        //wait 2 seconds.
                        Thread.Sleep(2000);

                    }
                    catch(Exception ex)
                    {
                        if (numberOfAttempts > maxTryCount)
                            break;
                    }
                    numberOfAttempts++;
                } while (numberOfAttempts < maxTryCount);

                var image = await  RestService.GetImageData(Helper.Values.DefaultImagePath, filename);
                //image.ContinueWith(aa =>
                //{
                    
                    byte[] imageArray = image.Data;
                    Log.Info(Helper.Values.LogTag, "image called with ");

                    Glide.With(this).AsBitmap().Load(image.Data).Into(newImage);
                    
                //}, TaskContinuationOptions.AttachedToParent);
            }
            else
            {
                Toast.MakeText(this, "filename not found...", ToastLength.Long);
            }



        }

        private void AddItem(string newString)
        {
            ArrayAdapter<string> adapter = (ArrayAdapter<string>)FindViewById<ListView>(Resource.Id.listView).Adapter;
            adapter.Add(newString);
        }

        public void BackTo()
        {
            var fr4 = new Intent(this, typeof(MainActivity));
            StartActivity(fr4);
        }

        



    }
}