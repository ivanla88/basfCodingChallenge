print('Start #################################################################');

db = db.getSiblingDB('chemicalPatents');
db.createUser(
  {
    user: 'chemicalsUser',
    pwd: 'chemicalsPass',
    roles: [
        { role: 'readWrite', db: 'chemicalPatents' }
    ],
  },
);

print('END #################################################################');