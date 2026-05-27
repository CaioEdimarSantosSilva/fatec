from django.contrib import admin
from django.conf import settings
from django.conf.urls.static import static
from django.contrib.auth.decorators import login_required
from django.contrib.auth import views as auth_views
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('app.urls')),
    path('api/', include('api.urls')),

    path("login/", auth_views.LoginView.as_view(template_name="login.html"), name="login"),
    path("logout/", auth_views.LogoutView.as_view(), name="logout"),

    path(
        "alterar-senha/",
        login_required(auth_views.PasswordChangeView.as_view(
            template_name="altera-senha.html",
            success_url="confirma"
        )),
        name="alterasenha"
    ),

    path(
        "alterar-senha/confirma/",
        login_required(auth_views.PasswordChangeDoneView.as_view(
            template_name="confima-senha.html"
        )),
        name="confirmasenha"
    ),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
