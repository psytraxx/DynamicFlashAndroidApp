This project is an Android application named "DynamicFlash" that serves as a photo gallery and project viewer. The application includes the following features:

- Photo Grid View: Displays photos in a grid layout using PhotoGridFragment and PhotoGridAdapter.
- Photo Swipe View: Allows users to swipe through photos in a full-screen view using PhotoSwipeActivity and PhotoSwipeAdapter.
- Gallery Management: Manages photo albums and displays them in a list using GalleryFragment and GalleryAdapter.
- Project Management: Displays project-related images in a swipeable view using ProjectFragment and ProjectSwipeAdapter.
- ViewModel Integration: Uses PhotoViewModel and PageViewModel to manage and observe data changes.
- Image Loading: Utilizes the Coil library for efficient image loading and caching.
- Navigation Drawer: Implements a navigation drawer for easy access to different sections of the app using MainActivity and DrawerAdapter.

The project is built with Android SDK 34, supports AndroidX, and uses Retrofit for network operations. It also includes a GitHub Actions workflow for continuous integration.
