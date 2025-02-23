import { bootstrapApplication } from '@angular/platform-browser';
import { provideServerRendering } from '@angular/platform-server';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { AppComponent } from './app/app.component';

export default function bootstrap() {
  return bootstrapApplication(AppComponent, {
    providers: [
      provideServerRendering(),
      provideHttpClient(withFetch())
    ]
  }).catch(err => console.error(err));
}
