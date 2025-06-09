import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { OrderComponent } from './order/order.component';
import { DetailProductComponent } from './detail-product/detail-product.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { OrderConfirmComponent } from './order-confirm/order-confirm.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    OrderComponent,
    DetailProductComponent,
    ProductDetailsComponent,
    OrderConfirmComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [BrowserModule],
  providers: [],
  bootstrap: [
    // HomeComponent,
    // OrderComponent,
    // OrderConfirmComponent,
    // LoginComponent,
    // ProductDetailsComponent,
    RegisterComponent,
  ],
})
export class AppModule {}
