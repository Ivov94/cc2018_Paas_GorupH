﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Android.App;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Org.Apache.Http.Client;
using Org.Apache.Http.Protocol;

namespace Paas.GroupH
{
    public class RestService
    {
        public static string Url = Helper.Values.DefaultHost + (Helper.Values.DefaultPort != "80" ? Helper.Values.DefaultPort : "") + Helper.Values.DefaultPath;

        public async static Task<string> PostData(Stream image, string filename)
        {
            string file = "file";
            var content = new MultipartFormDataContent();

            try {
                
                content.Add(new StreamContent(image), file, filename);

                var httpClient = new HttpClient();
                Log.Info(Helper.Values.LogTag, " posting image...");
                var httpResponseMessage = await httpClient.PostAsync(Url, content);
                Log.Info(Helper.Values.LogTag, "image uploaded...");

                Log.Info(Helper.Values.LogTag, "waiting for response from server...");
                var result = await httpResponseMessage.Content.ReadAsStringAsync();
                Log.Info(Helper.Values.LogTag, string.Format("result.. {0}", result)); ;

                return result;
            }
            catch(Exception ex)
            {
                return ex.Message;
            }
        }
    }
}