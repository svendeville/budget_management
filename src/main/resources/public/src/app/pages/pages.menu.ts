export const PAGES_MENU = [
  {
    path: 'pages',
    children: [
      {
        path: 'dashboard',
        data: {
          menu: {
            title: 'Tableau de bord',
            icon: 'ion-android-home',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      },
      {
        path: 'budget',
        data: {
          menu: {
            title: 'Budget',
            icon: 'ion-edit',
            selected: false,
            expanded: false,
            order: 100,
          }
        }
      },
      {
        path: 'bank-accounts',
        data: {
          menu: {
            title: 'Comptes',
            icon: 'ion-compose',
            selected: false,
            expanded: false,
            order: 110,
          }
        }
      },
      {
        path: 'parameters',
        data: {
          menu: {
            title: 'Paramètres',
            icon: 'ion-stats-bars',
            selected: false,
            expanded: false,
            order: 200,
          }
        },
        children: [
          {
            path: 'users',
            data: {
              menu: {
                title: 'Utilisateurs',
              }
            }
          },
          {
            path: 'categories',
            data: {
              menu: {
                title: 'Catégories',
              }
            }
          }
        ]
      }
    ]
  }
];
