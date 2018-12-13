using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Paas.GroupH.Helper
{
    public static class RetryHelper
    {
        public static void RetryOnNotFinishedJob(int times, TimeSpan delay, Action operation)
        {
            bool filterNegativeDone = false;
            bool filterRedDone = false;
            bool filterGreenDone = false;
            bool filterBlueDone = false;
            bool imageJoinDone = false;
            var attempts = 0;
            do
            {
                try
                {
                    attempts++;
                    operation();
                    break; // Sucess! Lets exit the loop!
                }
                catch (Exception ex)
                {
                    if (attempts == times)
                        throw;


                    Task.Delay(delay).Wait();
                }
            } while (true);
        }
    }
}