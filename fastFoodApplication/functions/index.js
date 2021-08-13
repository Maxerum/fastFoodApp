'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
const { Logging } = require('@google-cloud/logging');
const logging = new Logging({
  projectId: process.env.GCLOUD_PROJECT,
});
const stripe = require('stripe')(functions.config().stripe.secret, {
  apiVersion: '2020-03-02',
});

/**
 * When a user is created, create a Stripe customer object for them.
 */
exports.createStripeCustomer = functions.auth.user().onCreate(async (user) => {
  const customer = await stripe.customers.create({
    email: user.email,
    metadata: { firebaseUID: user.uid },
  });

  await admin.firestore().collection('customers').doc(user.uid).set({
    stripe_customer_id: customer.id,
  });
  return;
});

exports.createStripePayment = functions.https.onCall(async (data, context) => {
  // Checking that the user is authenticated.
  if (!context.auth) {
    // Throwing an HttpsError so that the client gets the error details.
    throw new functions.https.HttpsError(
      'failed-precondition',
      'The function must be called while authenticated!'
    );
  }
  const uid = context.auth.uid;
  try {
    if (!uid) throw new Error('Not authenticated!');
    // Get stripe customer id
    const customer = (
      await admin.firestore().collection('customers').doc(uid).get()
    ).data().stripe_customer_id;

    const key = await stripe.ephemeralKeys.create(
      { customer },
      { apiVersion: data.api_version }
    );
    const paymentIntent = await stripe.paymentIntents.create({
      amount: data.amount,
      currency: data.currency,
      customer: customer
    });

    const result = {
      paymentIntent: paymentIntent.client_secret,
      ephemeralKey: key.secret,
      customer: customer
    };
    return result;
  } catch (error) {
    throw new functions.https.HttpsError('internal', error.message);
  }
});
